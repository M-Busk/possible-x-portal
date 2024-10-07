package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateDto;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
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

    public ParticipantRegistrationServiceImpl(
        @Autowired ParticipantRegistrationRequestDAO participantRegistrationRequestDAO,
        @Autowired ParticipantRegistrationServiceMapper participantRegistrationServiceMapper,
        @Autowired OmejdnConnectorApiClient omejdnConnectorApiClient) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
        this.participantRegistrationServiceMapper = participantRegistrationServiceMapper;
        this.omejdnConnectorApiClient = omejdnConnectorApiClient;
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

    @Override
    public void completeRegistrationRequest(String id) {
        OmejdnConnectorCertificateDto certificate = requestDapsCertificate(id);
        log.info("Created DAPS digital identity {} for participant: {}", certificate.getClientId(), id);
      
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

    private OmejdnConnectorCertificateDto requestDapsCertificate(String clientName){
        return omejdnConnectorApiClient.addConnector(
            new OmejdnConnectorCertificateRequest(clientName));
    }
}
