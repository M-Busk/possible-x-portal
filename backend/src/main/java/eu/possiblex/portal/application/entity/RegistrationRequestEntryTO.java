package eu.possiblex.portal.application.entity;

import eu.possiblex.portal.application.entity.daps.OmejdnConnectorCertificateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestEntryTO {
    @Schema(description = "Registration number of the participant")
    private RegistrationNumberTO legalRegistrationNumber;

    @Schema(description = "Legal address of the participant")
    private AddressTO legalAddress;

    @Schema(description = "Headquarter address of the participant")
    private AddressTO headquarterAddress;

    @Schema(description = "Name of the participant", example = "Some Organization Ltd.")
    private String name;

    @Schema(description = "Description of the participant", example = "Some Organization Ltd. Description")
    private String description;

    @Schema(description = "Status of the registration request", example = "NEW")
    private RequestStatus status;

    @Schema(description = "Omejdn connector certificate of the participant")
    private OmejdnConnectorCertificateDto omejdnConnectorCertificate;

    @Schema(description = "Verifiable presentation link of the participant from the catalog", example = "https://catalog.possible-x.de/resources/legal-participant/did:web:example.com:participant:someorgltd")
    private String vpLink;

    @Schema(description = "DID data of the participant")
    private ParticipantDidDataTO didData;

    @Schema(description = "Email address of the participant", example = "contact@someorg.com")
    private String emailAddress;
}
