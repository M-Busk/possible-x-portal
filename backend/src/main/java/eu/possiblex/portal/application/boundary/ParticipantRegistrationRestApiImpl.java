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

package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.control.ParticipantRegistrationRestApiMapper;
import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ParticipantRegistrationRestApiImpl implements ParticipantRegistrationRestApi {

    private final ParticipantRegistrationService participantRegistrationService;

    private final ParticipantRegistrationRestApiMapper participantRegistrationRestApiMapper;

    public ParticipantRegistrationRestApiImpl(@Autowired ParticipantRegistrationService participantRegistrationService,
        @Autowired ParticipantRegistrationRestApiMapper participantRegistrationRestApiMapper) {

        this.participantRegistrationService = participantRegistrationService;
        this.participantRegistrationRestApiMapper = participantRegistrationRestApiMapper;
    }

    /**
     * Process and store a registration request for a participant.
     *
     * @param request participant registration request
     */
    @Override
    public void registerParticipant(@RequestBody CreateRegistrationRequestTO request) {

        log.info("Received participant registration request: {}", request);
        PxExtendedLegalParticipantCredentialSubject cs = participantRegistrationRestApiMapper.credentialSubjectsToExtendedLegalParticipantCs(
            request);

        participantRegistrationService.registerParticipant(cs);
    }

    /**
     * Get registration requests for the given pagination request.
     *
     * @return TO with list of registration requests
     */
    @Override
    public Page<RegistrationRequestEntryTO> getRegistrationRequests(Pageable paginationRequest) {

        log.info("Received request to get participant registration requests for page: {}", paginationRequest);

        return participantRegistrationService.getParticipantRegistrationRequests(paginationRequest);

    }

    @Override
    public RegistrationRequestEntryTO getRegistrationRequestByDid(@PathVariable String did) {

        log.info("Received request to get registration request for did {}", did);

        return participantRegistrationService.getParticipantRegistrationRequestByDid(did);
    }

    /**
     * Accept a registration request.
     *
     * @param id registration request id
     */
    @Override
    public void acceptRegistrationRequest(@PathVariable String id) {

        log.info("Received request to accept participant: {}", id);
        participantRegistrationService.acceptRegistrationRequest(id);
    }

    /**
     * Reject a registration request.
     *
     * @param id registration request id
     */
    @Override
    public void rejectRegistrationRequest(@PathVariable String id) {

        log.info("Received request to reject participant: {}", id);
        participantRegistrationService.rejectRegistrationRequest(id);
    }

    /**
     * Delete a registration request.
     *
     * @param id registration request id
     */
    @Override
    public void deleteRegistrationRequest(@PathVariable String id) {

        log.info("Received request to delete participant: {}", id);
        participantRegistrationService.deleteRegistrationRequest(id);
    }
}