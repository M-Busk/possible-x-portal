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

package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.CatalogCommunicationException;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;

public class FhCatalogClientFake implements FhCatalogClient {

    public static final String FAILING_PARTICIPANT_ID = "failingParticipantId";

    public static final String BAD_COMPLIANCE_PARTICIPANT_ID = "badComplianceParticipantId";

    @Override
    public FhCatalogIdResponse addParticipantToCatalog(PxExtendedLegalParticipantCredentialSubject cs) {

        if (cs.getId().equals(FAILING_PARTICIPANT_ID)) {
            throw new CatalogCommunicationException("Failing participant", new Exception());
        }

        if (cs.getId().equals(BAD_COMPLIANCE_PARTICIPANT_ID)) {
            throw new ParticipantComplianceException("Bad compliance participant", new Exception());
        }

        return new FhCatalogIdResponse(cs.getId());
    }

    @Override
    public PxExtendedLegalParticipantCredentialSubject getParticipantFromCatalog(String participantId) {

        return null;
    }

    @Override
    public void deleteParticipantFromCatalog(String participantId) throws ParticipantNotFoundException {
        // request worked
    }
}
