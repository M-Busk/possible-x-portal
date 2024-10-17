package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParticipantRegistrationServiceImpl implements ParticipantRegistrationService {

    private final ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    private final ParticipantRegistrationServiceMapper participantRegistrationServiceMapper;

    private final OmejdnConnectorApiClient omejdnConnectorApiClient;

    private final DidWebServiceApiClient didWebServiceApiClient;

    public ParticipantRegistrationServiceImpl(
        @Autowired ParticipantRegistrationRequestDAO participantRegistrationRequestDAO,
        @Autowired ParticipantRegistrationServiceMapper participantRegistrationServiceMapper,
        @Autowired OmejdnConnectorApiClient omejdnConnectorApiClient,
        @Autowired DidWebServiceApiClient didWebServiceApiClient) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
        this.participantRegistrationServiceMapper = participantRegistrationServiceMapper;
        this.omejdnConnectorApiClient = omejdnConnectorApiClient;
        this.didWebServiceApiClient = didWebServiceApiClient;
    }

    /**
     * Given a registration request, process and store it for later use.
     *
     * @param cs request
     */
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs, ParticipantMetadataBE be) {

        log.info("Processing participant registration: {}", cs);

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(cs, be);
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
    public void acceptRegistrationRequest(String id) {

        log.info("Processing acceptance of participant: {}", id);

        participantRegistrationRequestDAO.acceptRegistrationRequest(id);
        completeRegistrationRequest(id);
    }

    private void completeRegistrationRequest(String id) {
        ParticipantDidBE didWeb = generateDidWeb(id);
        log.info("Created did {} for participant: {}", didWeb, id);
        participantRegistrationRequestDAO.storeRegistrationRequestDid(id, didWeb);

        OmejdnConnectorCertificateBE certificate = requestDapsCertificate(didWeb.getDid());
        log.info("Created DAPS digital identity {} for participant: {}", certificate.getClientId(), id);
        participantRegistrationRequestDAO.storeRegistrationRequestDaps(id, certificate);

        String vpLink = getVPLink();
        log.info("Received VP {} for participant: {}", vpLink, id);
        participantRegistrationRequestDAO.storeRegistrationRequestVpLink(id, vpLink);

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

    private String getVPLink() {

        return "www.example.com";
    }
}
