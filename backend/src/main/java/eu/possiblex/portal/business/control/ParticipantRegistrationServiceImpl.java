package eu.possiblex.portal.business.control;

import com.fasterxml.jackson.databind.JsonNode;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
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

    public ParticipantRegistrationServiceImpl(
        @Autowired ParticipantRegistrationRequestDAO participantRegistrationRequestDAO,
        @Autowired ParticipantRegistrationServiceMapper participantRegistrationServiceMapper,
        @Autowired OmejdnConnectorApiClient omejdnConnectorApiClient,
        @Autowired DidWebServiceApiClient didWebServiceApiClient, @Autowired FhCatalogClient fhCatalogClient,
        @Value("${fh.catalog.url}") String fhCatalogUrl) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
        this.participantRegistrationServiceMapper = participantRegistrationServiceMapper;
        this.omejdnConnectorApiClient = omejdnConnectorApiClient;
        this.didWebServiceApiClient = didWebServiceApiClient;
        this.fhCatalogClient = fhCatalogClient;
        this.fhCatalogParticipantBaseUrl = fhCatalogUrl + "/resources/legal-participant/";
    }

    /**
     * Given a registration request, process and store it for later use.
     *
     * @param cs request
     */
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {

        log.info("Processing participant registration: {}", cs);

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

        return participantRegistrationRequestDAO.getAllParticipantRegistrationRequests().stream()
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

        // generate organisation identity for participant
        ParticipantDidBE didWeb = generateDidWeb(id);
        log.info("Created did {} for participant: {}", didWeb, id);
        participantRegistrationRequestDAO.storeRegistrationRequestDid(id, didWeb);

        // retrieve full request from db (including did)
        ParticipantRegistrationRequestBE be = participantRegistrationRequestDAO.getRegistrationRequestByDid(
            didWeb.getDid());

        // build credential subject and enroll participant in catalog
        String vpLink = enrollParticipantInCatalog(be);
        log.info("Received VP {} for participant: {}", vpLink, id);
        participantRegistrationRequestDAO.storeRegistrationRequestVpLink(id, vpLink);

        // generate consumer/provider component identity
        OmejdnConnectorCertificateBE certificate = requestDapsCertificate(didWeb.getDid());
        log.info("Created DAPS digital identity {} for participant: {}", certificate.getClientId(), id);
        participantRegistrationRequestDAO.storeRegistrationRequestDaps(id, certificate);

        // set request to completed
        participantRegistrationRequestDAO.completeRegistrationRequest(id);
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

    private OmejdnConnectorCertificateBE requestDapsCertificate(String clientName) {

        return omejdnConnectorApiClient.addConnector(new OmejdnConnectorCertificateRequest(clientName));
    }

    private ParticipantDidBE generateDidWeb(String id) {

        ParticipantDidCreateRequestBE createRequestTo = new ParticipantDidCreateRequestBE();
        createRequestTo.setSubject(id);
        return didWebServiceApiClient.generateDidWeb(createRequestTo);
    }

    private String enrollParticipantInCatalog(ParticipantRegistrationRequestBE be)
        throws ParticipantComplianceException {

        PxExtendedLegalParticipantCredentialSubject cs = participantRegistrationServiceMapper.participantRegistrationRequestBEToCs(
            be);

        // for local testing
        //cs.setId("did:web:didwebservice.dev.possible-x.de:participant:0a527305-97fb-3ffa-81fc-117d9e71e3a9");

        try {
            FhCatalogIdResponse idResponse = fhCatalogClient.addParticipantToCatalog(cs);
            log.info("Stored CS for participant {} in catalog: {}", idResponse, cs);

            return fhCatalogParticipantBaseUrl + idResponse.getId();
        } catch (WebClientResponseException.UnprocessableEntity e) {
            JsonNode error = e.getResponseBodyAs(JsonNode.class);
            if (error != null && error.get("error") != null) {
                throw new ParticipantComplianceException(error.get("error").textValue(), e);
            }
            throw new ParticipantComplianceException("Unknown catalog processing exception", e);
        }
    }
}
