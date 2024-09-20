package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.RegistrationRequestTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/registration")
public interface ParticipantRegistrationRestApi {
    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    void registerParticipant(@RequestBody RegistrationRequestTO request);
}