/*
 *  Copyright 2024 Dataport. All rights reserved. Developed as part of the MERLOT project.
 *  Copyright 2024-2025 Dataport. All rights reserved. Extended as part of the POSSIBLE project.
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

package eu.possiblex.portal.application.entity.daps;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmejdnConnectorCertificateDto {
    @Schema(description = "DAPS client name of the participant", example = "did:web:example.com:participant:someorgltd")
    @JsonProperty("client_name")
    private String clientName;

    @Schema(description = "DAPS client ID of the participant", example = "A4:22:E0:E1:E6:7A:0F:D7:AD:11:2B:A1:D9:58:1E:38:62:2C:13:31:keyid:A4:22:E0:E1:E6:7A:0F:D7:AD:11:2B:A1:D9:58:1E:38:62:2C:13:31")
    @JsonProperty("client_id")
    private String clientId;

    @Schema(description = "Certificate keystore as base64 encoded string", example = "a2V5c3RvcmU=")
    @JsonProperty("keystore")
    private String keystore;

    @Schema(description = "Password for the certificate keystore", example = "password")
    @JsonProperty("password")
    private String password;

    @Schema(description = "Scope for fetching claims", example = "idsc:IDS_CONNECTOR_ATTRIBUTES_ALL")
    @JsonProperty("scope")
    private String scope;
}