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

package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipantRegistrationRequestDAO {
    /**
     * Save a participant registration request.
     *
     * @param participant registration request CS
     */
    void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant);

    /**
     * Given a registration request id, accept the registration request.
     *
     * @param id registration request id
     */
    void acceptRegistrationRequest(String id);

    /**
     * Given a registration request id, reject the registration request.
     *
     * @param id registration request id
     */
    void rejectRegistrationRequest(String id);

    /**
     * Given a registration request id, delete the registration request.
     *
     * @param id registration request id
     */
    void deleteRegistrationRequest(String id);

    void completeRegistrationRequest(String id, ParticipantDidBE did, String vpLink,
        OmejdnConnectorCertificateBE certificate);

    /**
     * Get a list of registration requests for given pageable.
     *
     * @return list of registration requests
     */
    Page<ParticipantRegistrationRequestBE> getRegistrationRequests(Pageable paginationRequest);

    /**
     * Get a registration request by DID.
     *
     * @param did DID
     * @return registration request
     */
    ParticipantRegistrationRequestBE getRegistrationRequestByDid(String did);

    ParticipantRegistrationRequestBE getRegistrationRequestByName(String name);
}
