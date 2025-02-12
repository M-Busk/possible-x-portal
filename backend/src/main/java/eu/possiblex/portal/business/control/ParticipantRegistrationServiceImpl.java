package eu.possiblex.portal.business.control;

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
import eu.possiblex.portal.business.entity.exception.RegistrationRequestConflictException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestProcessingException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityNotFoundException;
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityStateTransitionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        @Value("${fh.catalog.url}") String fhCatalogUrl,
        @Value("${daps-server.url.external}") String dapsServerExternalUrl) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
        this.participantRegistrationServiceMapper = participantRegistrationServiceMapper;
        this.omejdnConnectorApiClient = omejdnConnectorApiClient;
        this.didWebServiceApiClient = didWebServiceApiClient;
        this.fhCatalogClient = fhCatalogClient;
        this.fhCatalogParticipantBaseUrl = fhCatalogUrl + "/resources/legal-participant/";
        this.dapsIdBaseUrl = dapsServerExternalUrl + "/api/v1/connectors/";
    }

    /**
     * Given a registration request, process and store it for later use.
     *
     * @param cs request
     */
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {

        log.info("Processing participant registration: {}", cs);

        ParticipantRegistrationRequestBE be = participantRegistrationRequestDAO.getRegistrationRequestByName(
            cs.getName());

        if (be != null) {
            throw new RegistrationRequestConflictException(
                "A registration request has already been made under this organization name: " + cs.getName());
        }

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(cs);
    }

    /**
     * Get registration requests for a given page number and page size sorted by sort field and sort order.
     *
     * @return TO with list of registration requests
     */
    @Override
    public Page<RegistrationRequestEntryTO> getParticipantRegistrationRequests(Pageable paginationRequest) {

        log.info("Processing retrieval of participant registration requests for page {}", paginationRequest);
        Page<ParticipantRegistrationRequestBE> registrationRequests = participantRegistrationRequestDAO.getRegistrationRequests(
            paginationRequest);
        return registrationRequests.map(
            participantRegistrationServiceMapper::participantRegistrationRequestBEToRegistrationRequestEntryTO);
    }

    @Override
    public RegistrationRequestEntryTO getParticipantRegistrationRequestByDid(String did) {

        log.info("Processing retrieval of participant registration requests with did {}", did);

        ParticipantRegistrationRequestBE be = participantRegistrationRequestDAO.getRegistrationRequestByDid(did);

        if (be == null) {
            throw new ParticipantNotFoundException("No registration request found for DID: " + did);
        }

        return participantRegistrationServiceMapper.participantRegistrationRequestBEToRegistrationRequestEntryTO(be);
    }

    /**
     * Given a registration request id, accept the registration request.
     *
     * @param id registration request id
     */
    @Override
    public void acceptRegistrationRequest(String id) {

        log.info("Processing acceptance of participant: {}", id);

        try {
            participantRegistrationRequestDAO.acceptRegistrationRequest(id);
        } catch (Exception e) {
            handleEntityProcessingException(e);
        }

        completeRegistrationRequest(id);
    }

    private void completeRegistrationRequest(String id) {

        // load registration request entity from db
        ParticipantRegistrationRequestBE be = participantRegistrationRequestDAO.getRegistrationRequestByName(id);

        // generate organisation identity for participant
        ParticipantDidBE didWeb = generateDidWeb(id);
        log.info("Created did {} for participant: {}", didWeb, id);

        // build credential subject and enroll participant in catalog
        FhCatalogIdResponse idResponse;
        try {
            idResponse = enrollParticipantInCatalog(be, didWeb.getDid());
        } catch (Exception e) {
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
        } catch (Exception e) {
            // revert catalog and did if daps won't work
            deleteParticipantFromCatalog(idResponse.getId());
            deleteDidWeb(didWeb.getDid());
            throw e;
        }
        log.info("Created DAPS digital identity {} for participant: {}", certificate.getClientId(), id);

        String dapsIdUrl = dapsIdBaseUrl + certificate.getClientId();
        try {
            updateDidWebWithAliases(didWeb.getDid(), List.of(dapsIdUrl));
        } catch (Exception e) {
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
            handleEntityProcessingException(e);
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

        try {
            participantRegistrationRequestDAO.rejectRegistrationRequest(id);
        } catch (Exception e) {
            handleEntityProcessingException(e);
        }

    }

    /**
     * Given a registration request id, delete the registration request.
     *
     * @param id registration request id
     */
    @Override
    public void deleteRegistrationRequest(String id) {

        log.info("Processing deletion of participant: {}", id);

        try {
            participantRegistrationRequestDAO.deleteRegistrationRequest(id);
        } catch (Exception e) {
            handleEntityProcessingException(e);
        }

    }

    private OmejdnConnectorCertificateBE requestDapsCertificate(String clientName, String did) {

        try {
            return omejdnConnectorApiClient.addConnector(new OmejdnConnectorCertificateRequest(clientName, did));
        } catch (Exception e) {
            log.error("Failed to request DAPS certificate {}", clientName, e);
            throw new RegistrationRequestProcessingException("Failed to request DAPS certificate", e);
        }
    }

    private void deleteDapsCertificate(String clientId) {

        try {
            omejdnConnectorApiClient.deleteConnector(clientId);
        } catch (Exception e) {
            log.error("Failed to delete DAPS certificate {}", clientId, e);
        }
    }

    private ParticipantDidBE generateDidWeb(String id) {

        ParticipantDidCreateRequestBE createRequestBE = new ParticipantDidCreateRequestBE();
        createRequestBE.setSubject(id);
        try {
            return didWebServiceApiClient.generateDidWeb(createRequestBE);
        } catch (Exception e) {
            log.error("Failed to generate DID {}", id, e);
            throw new RegistrationRequestProcessingException("Failed to generate DID", e);
        }
    }

    private void updateDidWebWithAliases(String did, List<String> aliases) {

        ParticipantDidUpdateRequestBE updateRequestBE = new ParticipantDidUpdateRequestBE(did, aliases);
        try {
            didWebServiceApiClient.updateDidWeb(updateRequestBE);
        } catch (Exception e) {
            log.error("Failed to update DID {}", did, e);
            throw new RegistrationRequestProcessingException("Failed to update DID document with DAPS ID", e);
        }
    }

    private void deleteDidWeb(String id) {

        try {
            didWebServiceApiClient.deleteDidWeb(id);
        } catch (Exception e) {
            log.error("Failed to delete DID {}", id, e);
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
        } catch (ParticipantComplianceException e) {
            log.error("Participant {} does not fulfill compliance", cs);
            throw e;
        } catch (Exception e) {
            log.error("Failed to store participant in catalog", e);
            throw new RegistrationRequestProcessingException("Failed to store participant in catalog", e);
        }
    }

    private void deleteParticipantFromCatalog(String id) {

        try {
            fhCatalogClient.deleteParticipantFromCatalog(id);
        } catch (Exception e) {
            log.error("Failed to delete participant from catalog", e);
        }
    }

    private void handleEntityProcessingException(Exception e) {

        if (e instanceof ParticipantEntityNotFoundException notFoundException) {
            throw new ParticipantNotFoundException(notFoundException.getMessage());
        } else if (e instanceof ParticipantEntityStateTransitionException transitionException) {
            throw new RegistrationRequestProcessingException(
                "Cannot transition participant registration request: " + transitionException.getMessage(), e);
        } else {
            throw new RegistrationRequestProcessingException("Unknown error during request processing", e);
        }
    }
}
