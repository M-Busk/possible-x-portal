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

import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.px.participants.PxParticipantExtensionCredentialSubject;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Participant credential subject")
    @Valid
    @NotNull(message = "Participant credential subject is required")
    private GxLegalParticipantCredentialSubject participantCs;

    @Schema(description = "Registration number credential subject")
    @Valid
    @NotNull(message = "Registration number credential subject is required")
    private GxLegalRegistrationNumberCredentialSubject registrationNumberCs;

    @Schema(description = "Participant extension credential subject")
    @Valid
    @NotNull(message = "Participant extension credential subject is required")
    private PxParticipantExtensionCredentialSubject participantExtensionCs;
}
