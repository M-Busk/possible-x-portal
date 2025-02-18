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

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestConflictException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

public class ParticipantRegistrationServiceFake implements ParticipantRegistrationService {

    public static final String CONFLICT_NAME = "conflict";
    public static final String PROCESSING_ERROR_NAME = "processing-error";
    public static final String BAD_COMPLIANCE_NAME = "bad-compliance";



    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {

        switch (cs.getName()) {
            case CONFLICT_NAME -> throw new RegistrationRequestConflictException("Conflict");
            case PROCESSING_ERROR_NAME -> throw new RegistrationRequestProcessingException("Processing error");
            case BAD_COMPLIANCE_NAME -> throw new ParticipantComplianceException("Bad compliance");
            default -> {
                // request worked
            }
        }
    }

    @Override
    public Page<RegistrationRequestEntryTO> getParticipantRegistrationRequests(Pageable paginationRequest) {

        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public RegistrationRequestEntryTO getParticipantRegistrationRequestByDid(String did) {

        return null;
    }

    @Override
    public void acceptRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void rejectRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void deleteRegistrationRequest(String id) {
        // request worked
    }
}
