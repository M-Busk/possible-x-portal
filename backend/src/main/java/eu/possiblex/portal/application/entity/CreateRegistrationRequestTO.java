package eu.possiblex.portal.application.entity;

import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistrationRequestTO {
    private GxLegalParticipantCredentialSubject participantCs;

    private GxLegalRegistrationNumberCredentialSubject registrationNumberCs;

    private String emailAddress;
}
