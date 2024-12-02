package eu.possiblex.portal.business.control;

import com.fasterxml.jackson.databind.JsonNode;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidUpdateRequestBE;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Slf4j
public class ParticipantRegistrationServiceImpl implements ParticipantRegistrationService {

    private final ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    private final ParticipantRegistrationServiceMapper participantRegistrationServiceMapper;

    private final OmejdnConnectorApiClient omejdnConnectorApiClient;

    private final DidWebServiceApiClient didWebServiceApiClient;

    private final FhCatalogClient fhCatalogClient;

    private final String fhCatalogParticipantBaseUrl;

    private final String dapsIdBaseUrl;

    public ParticipantRegistrationServiceImpl(
        @Autowired ParticipantRegistrationRequestDAO participantRegistrationRequestDAO,
        @Autowired ParticipantRegistrationServiceMapper participantRegistrationServiceMapper,
        @Autowired OmejdnConnectorApiClient omejdnConnectorApiClient,
        @Autowired DidWebServiceApiClient didWebServiceApiClient, @Autowired FhCatalogClient fhCatalogClient,
        @Value("${fh.catalog.url}") String fhCatalogUrl, @Value("${daps-server.base-url}") String dapsServerBaseUrl) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
        this.participantRegistrationServiceMapper = participantRegistrationServiceMapper;
        this.omejdnConnectorApiClient = omejdnConnectorApiClient;
        this.didWebServiceApiClient = didWebServiceApiClient;
        this.fhCatalogClient = fhCatalogClient;
        this.fhCatalogParticipantBaseUrl = fhCatalogUrl + "/resources/legal-participant/";
        this.dapsIdBaseUrl = dapsServerBaseUrl + "/api/v1/connectors/";
    }

    /**
     * Given a registration request, process and store it for later use.
     *
     * @param cs request
     */
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {

        log.info("Processing participant registration: {}", cs);

        if (participantRegistrationRequestDAO.getRegistrationRequestByName(cs.getName()) != null) {
            throw new RegistrationRequestException(
                "A registration request has already been made under this organization name: " + cs.getName());
        }

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(cs);
    }

    /**
     * Get all registration requests.
     *
     * @return list of registration requests
     */
    @Override
    public List<RegistrationRequestEntryTO> getAllParticipantRegistrationRequests() {

        log.info("Processing retrieval of all participant registration requests");

        return participantRegistrationRequestDAO.getAllRegistrationRequests().stream()
            .map(participantRegistrationServiceMapper::participantRegistrationRequestBEToRegistrationRequestEntryTO)
            .toList();
    }

    @Override
    public RegistrationRequestEntryTO getParticipantRegistrationRequestByDid(String did) {

        log.info("Processing retrieval of participant registration requests with did {}", did);

        return participantRegistrationServiceMapper.participantRegistrationRequestBEToRegistrationRequestEntryTO(
            participantRegistrationRequestDAO.getRegistrationRequestByDid(did));
    }

    /**
     * Given a registration request id, accept the registration request.
     *
     * @param id registration request id
     */
    @Override
    public void acceptRegistrationRequest(String id) throws ParticipantComplianceException {

        log.info("Processing acceptance of participant: {}", id);

        participantRegistrationRequestDAO.acceptRegistrationRequest(id);
        completeRegistrationRequest(id);
    }

    private void completeRegistrationRequest(String id) throws ParticipantComplianceException {

        // load registration request entity from db
        ParticipantRegistrationRequestBE be = participantRegistrationRequestDAO.getRegistrationRequestByName(id);

        // generate organisation identity for participant
        ParticipantDidBE didWeb = generateDidWeb(id);
        log.info("Created did {} for participant: {}", didWeb, id);

        // build credential subject and enroll participant in catalog
        FhCatalogIdResponse idResponse;
        try {
            idResponse = enrollParticipantInCatalog(be, didWeb.getDid());
        } catch (ParticipantComplianceException e) {
            // revert did if catalog won't work
            deleteDidWeb(didWeb.getDid());
            throw e;
        }
        String vpLink = fhCatalogParticipantBaseUrl + idResponse.getId();
        log.info("Received VP {} for participant: {}", vpLink, id);

        // generate consumer/provider component identity
        // currently we set both the (daps internal) id and the attested did to the same value
        OmejdnConnectorCertificateBE certificate;
        try {
            certificate = requestDapsCertificate(didWeb.getDid(), didWeb.getDid());
        } catch (RegistrationRequestException e) {
            // revert catalog and did if daps won't work
            deleteParticipantFromCatalog(idResponse.getId());
            deleteDidWeb(didWeb.getDid());
            throw e;
        }
        log.info("Created DAPS digital identity {} for participant: {}", certificate.getClientId(), id);

        String dapsIdUrl = dapsIdBaseUrl + certificate.getClientId();
        try {
            updateDidWebWithAliases(didWeb.getDid(), List.of(dapsIdUrl));
        } catch (RegistrationRequestException e) {
            // revert all changes if something goes wrong
            deleteDapsCertificate(certificate.getClientId());
            deleteParticipantFromCatalog(idResponse.getId());
            deleteDidWeb(didWeb.getDid());
            throw e;
        }
        log.info("Updated did document {} with daps identity {}", didWeb.getDid(), dapsIdUrl);

        // set request to completed
        try {
            participantRegistrationRequestDAO.completeRegistrationRequest(id, didWeb, vpLink, certificate);
        } catch (Exception e) {
            // revert all changes if something goes wrong
            deleteDapsCertificate(certificate.getClientId());
            deleteParticipantFromCatalog(idResponse.getId());
            deleteDidWeb(didWeb.getDid());
            throw e;
        }
        log.info("Stored finalized registration request for participant: {}", id);
    }

    /**
     * Given a registration request id, reject the registration request.
     *
     * @param id registration request id
     */
    @Override
    public void rejectRegistrationRequest(String id) {

        log.info("Processing rejection of participant: {}", id);

        participantRegistrationRequestDAO.rejectRegistrationRequest(id);
    }

    /**
     * Given a registration request id, delete the registration request.
     *
     * @param id registration request id
     */
    @Override
    public void deleteRegistrationRequest(String id) {

        log.info("Processing deletion of participant: {}", id);
        participantRegistrationRequestDAO.deleteRegistrationRequest(id);
    }

    private OmejdnConnectorCertificateBE requestDapsCertificate(String clientName, String did) {

        try {
            return omejdnConnectorApiClient.addConnector(new OmejdnConnectorCertificateRequest(clientName, did));
        } catch (WebClientResponseException e) {
            log.error("Failed to request DAPS certificate: {}", e.getResponseBodyAsString());
            throw new RegistrationRequestException("Failed to request DAPS certificate", e);
        }
    }

    private void deleteDapsCertificate(String clientId) {

        try {
            omejdnConnectorApiClient.deleteConnector(clientId);
        } catch (WebClientResponseException e) {
            log.error("Failed to delete DAPS certificate", e);
        }
    }

    private ParticipantDidBE generateDidWeb(String id) {

        ParticipantDidCreateRequestBE createRequestBE = new ParticipantDidCreateRequestBE();
        createRequestBE.setSubject(id);
        try {
            return didWebServiceApiClient.generateDidWeb(createRequestBE);
        } catch (WebClientResponseException e) {
            log.error("Failed to generate DID: {}", e.getResponseBodyAsString());
            throw new RegistrationRequestException("Failed to generate DID", e);
        }
    }

    private void updateDidWebWithAliases(String did, List<String> aliases) {

        ParticipantDidUpdateRequestBE updateRequestBE = new ParticipantDidUpdateRequestBE(did, aliases);
        try {
            didWebServiceApiClient.updateDidWeb(updateRequestBE);
        } catch (WebClientResponseException e) {
            log.error("Failed to update DID: {}", e.getResponseBodyAsString());
            throw new RegistrationRequestException("Failed to update DID document with DAPS ID", e);
        }
    }

    private void deleteDidWeb(String id) {

        try {
            didWebServiceApiClient.deleteDidWeb(id);
        } catch (WebClientResponseException e) {
            log.error("Failed to delete DID: {}", e.getResponseBodyAsString());
        }
    }

    private FhCatalogIdResponse enrollParticipantInCatalog(ParticipantRegistrationRequestBE be, String did)
        throws ParticipantComplianceException {

        PxExtendedLegalParticipantCredentialSubject cs = participantRegistrationServiceMapper.participantRegistrationRequestBEToCs(
            be);
        cs.setId(did);

        try {
            FhCatalogIdResponse idResponse = fhCatalogClient.addParticipantToCatalog(cs);
            log.info("Stored CS for participant {} in catalog: {}", idResponse, cs);

            return idResponse;
        } catch (WebClientResponseException.UnprocessableEntity e) {
            JsonNode error = e.getResponseBodyAs(JsonNode.class);
            if (error != null && error.get("error") != null) {
                throw new ParticipantComplianceException(error.get("error").textValue(), e);
            }
            throw new ParticipantComplianceException("Unknown catalog processing exception", e);
        }
    }

    private void deleteParticipantFromCatalog(String id) {

        try {
            fhCatalogClient.deleteParticipantFromCatalog(id);
        } catch (WebClientResponseException | ParticipantNotFoundException e) {
            log.error("Failed to delete participant from catalog", e);
        }
    }
}
