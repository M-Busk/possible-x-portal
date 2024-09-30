package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.control.ParticipantCredentialMapper;
import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
import eu.possiblex.portal.application.entity.RegistrationRequestTO;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*") // TODO replace this with proper CORS configuration
@Slf4j
public class ParticipantRegistrationRestApiImpl implements ParticipantRegistrationRestApi {

    private final ParticipantRegistrationService participantRegistrationService;

    private final ParticipantCredentialMapper participantCredentialMapper;

    public ParticipantRegistrationRestApiImpl(@Autowired ParticipantRegistrationService participantRegistrationService,
        @Autowired ParticipantCredentialMapper participantCredentialMapper) {

        this.participantRegistrationService = participantRegistrationService;
        this.participantCredentialMapper = participantCredentialMapper;
    }

    /**
     * Process and store a registration request for a participant.
     *
     * @param request participant registration request
     */
    @Override
    public void registerParticipant(@RequestBody RegistrationRequestTO request) {

        log.info("Received participant registration request: {}", request);

        PxExtendedLegalParticipantCredentialSubject be = participantCredentialMapper.credentialSubjectsToExtendedLegalParticipantCs(
            request.getParticipantCs(), request.getRegistrationNumberCs());

        participantRegistrationService.registerParticipant(be);
    }

    @Override
    public List<RegistrationRequestListTO> getAllRegistrationRequests() {

        log.info("Received request to get all participant registration requests");

        return participantRegistrationService.getAllParticipantRegistrationRequests();

    }
}