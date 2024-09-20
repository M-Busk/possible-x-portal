package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.RegistrationRequestTO;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*") // TODO replace this with proper CORS configuration
@Slf4j
public class ParticipantRegistrationRestApiImpl implements ParticipantRegistrationRestApi {

    private final ParticipantRegistrationService participantRegistrationService;

    public ParticipantRegistrationRestApiImpl(@Autowired ParticipantRegistrationService participantRegistrationService) {
        this.participantRegistrationService = participantRegistrationService;
    }

    @Override
    public void registerParticipant(RegistrationRequestTO request) {
        throw new NotImplementedException("Not implemented yet");
    }
}