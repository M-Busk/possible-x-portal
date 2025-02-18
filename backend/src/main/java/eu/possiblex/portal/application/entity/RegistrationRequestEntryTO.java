/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
