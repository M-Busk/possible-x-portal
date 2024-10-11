package eu.possiblex.portal.business.entity;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@Builder
public class ParticipantRegistrationRequestBE {

    private GxLegalRegistrationNumberCredentialSubject legalRegistrationNumber;

    private GxVcard legalAddress;

    private GxVcard headquarterAddress;

    private String name;

    private String description;

    private RequestStatus status;
}
