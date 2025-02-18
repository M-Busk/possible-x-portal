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

import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidUpdateRequestBE;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface DidWebServiceApiClient {

    @PostExchange("/internal/didweb")
    ParticipantDidBE generateDidWeb(@RequestBody ParticipantDidCreateRequestBE request);

    @DeleteExchange("/internal/didweb/{did}")
    void deleteDidWeb(@PathVariable String did);

    @PatchExchange("/internal/didweb")
    void updateDidWeb(@RequestBody ParticipantDidUpdateRequestBE request);

    @GetExchange("/participant/{id}/did.json")
    String getDidDocument(@PathVariable(value = "id") String id);
}
