package eu.possiblex.portal.application.entity;

import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.px.participants.PxParticipantExtensionCredentialSubject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistrationRequestTO {

    @Valid
    @NotNull(message = "Participant credential subject is required")
    private GxLegalParticipantCredentialSubject participantCs;

    @Valid
    @NotNull(message = "Registration number credential subject is required")
    private GxLegalRegistrationNumberCredentialSubject registrationNumberCs;

    @Valid
    @NotNull(message = "Participant extension credential subject is required")
    private PxParticipantExtensionCredentialSubject participantExtensionCs;
}
