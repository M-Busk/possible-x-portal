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

package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import io.netty.util.internal.StringUtil;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

public class OmejdnConnectorApiClientFake implements OmejdnConnectorApiClient {

    public static final String FAILING_NAME = "failDaps";

    @Override
    public OmejdnConnectorCertificateBE addConnector(OmejdnConnectorCertificateRequest request) {

        if (request.getClientName().equals(FAILING_NAME)) {
            throw WebClientResponseException.create(500, "daps creation failed", null, null, null);
        }

        OmejdnConnectorCertificateBE dto = new OmejdnConnectorCertificateBE();
        dto.setClientId("12:34:56");
        dto.setClientName((StringUtil.isNullOrEmpty(request.getClientName()))
            ? UUID.randomUUID().toString()
            : request.getClientName());
        dto.setKeystore("keystore123");
        dto.setPassword("password1234");
        return dto;
    }

    @Override
    public void deleteConnector(String clientId) {
        // request worked
    }
}
