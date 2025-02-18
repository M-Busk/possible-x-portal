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

package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationRestApiMapper {

    @Mapping(target = "legalRegistrationNumber", source = "registrationNumberCs")
    @Mapping(target = "legalAddress", source = "participantCs.legalAddress")
    @Mapping(target = "headquarterAddress", source = "participantCs.headquarterAddress")
    @Mapping(target = "name", source = "participantCs.name")
    @Mapping(target = "description", source = "participantCs.description")
    @Mapping(target = "mailAddress", source = "participantExtensionCs.mailAddress")
    @Mapping(target = "id", ignore = true)
    PxExtendedLegalParticipantCredentialSubject credentialSubjectsToExtendedLegalParticipantCs(
        CreateRegistrationRequestTO request);
}

