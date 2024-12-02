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
 *
 * Modifications:
 * - Dataport (part of the POSSIBLE project) - 14 August, 2024 - Adjust package names and imports
 */

package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

/**
 * The technical class to access the FH Catalog via REST.
 */
public interface TechnicalFhCatalogClient {

    @PutExchange("/trust/legal-participant")
    FhCatalogIdResponse addParticipantToFhCatalog(
        @RequestBody PxExtendedLegalParticipantCredentialSubject participantCs);

    @GetExchange("/resources/legal-participant/{participantId}")
    String getParticipantFromCatalog(@PathVariable String participantId);

    @DeleteExchange("/resources/legal-participant/{participantId}")
    void deleteParticipantFromCatalog(@PathVariable String participantId);
}

