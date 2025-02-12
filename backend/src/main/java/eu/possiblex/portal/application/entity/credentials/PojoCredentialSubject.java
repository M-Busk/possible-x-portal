/*
 *  Copyright 2024 Dataport. All rights reserved. Developed as part of the MERLOT project.
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

package eu.possiblex.portal.application.entity.credentials;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.px.participants.PxParticipantExtensionCredentialSubject;
import lombok.*;

@Getter
@Setter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = UnknownCredentialSubject.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GxLegalParticipantCredentialSubject.class, name = GxLegalParticipantCredentialSubject.TYPE),
    @JsonSubTypes.Type(value = GxLegalRegistrationNumberCredentialSubject.class, name = GxLegalRegistrationNumberCredentialSubject.TYPE),
    @JsonSubTypes.Type(value = PxParticipantExtensionCredentialSubject.class, name = PxParticipantExtensionCredentialSubject.TYPE), })
@NoArgsConstructor
@AllArgsConstructor
public abstract class PojoCredentialSubject {
    // base fields
    // no input validations as this will be set by the backend
    private String id;
}

