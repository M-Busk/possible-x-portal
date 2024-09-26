package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.control.ParticipantCredentialMapper;
import eu.possiblex.portal.application.entity.RegistrationRequestTO;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public void registerParticipant(@RequestBody RegistrationRequestTO request) {

        log.info("Received participant registration request: {}", request);

        PossibleParticipantBE be = participantCredentialMapper.credentialSubjectsToBE(request.getParticipantCs(),
            request.getRegistrationNumberCs());

        participantRegistrationService.registerParticipant(be);
    }
}