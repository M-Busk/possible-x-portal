package eu.possiblex.portal.application.entity;

import eu.possiblex.portal.application.entity.daps.OmejdnConnectorCertificateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestEntryTO {
    private RegistrationNumberTO legalRegistrationNumber;

    private AddressTO legalAddress;

    private AddressTO headquarterAddress;

    private String name;

    private String description;

    private RequestStatus status;

    private OmejdnConnectorCertificateDto omejdnConnectorCertificate;

    private String vpLink;

    private ParticipantDidDataTO didData;

    private String emailAddress;
}
