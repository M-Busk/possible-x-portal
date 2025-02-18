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
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class DidWebServiceApiClientFake implements DidWebServiceApiClient {

    public static final String EXAMPLE_DID = "did:web:example.com:participant:1234";

    public static final String EXAMPLE_VERIFICATION_METHOD = "did:web:example.com:participant:1234#JWK2020-Example";

    public static final String FAILING_REQUEST_SUBJECT = "failDid";

    public static final String FAILING_UPDATE_DID = "failUpdateDid";

    @Override
    public ParticipantDidBE generateDidWeb(ParticipantDidCreateRequestBE request) {

        if (request.getSubject().equals(FAILING_REQUEST_SUBJECT)) {
            throw WebClientResponseException.create(500, "did creation failed", null, null, null);
        }

        ParticipantDidBE be = new ParticipantDidBE();
        be.setDid(request.getSubject());
        be.setVerificationMethod(EXAMPLE_VERIFICATION_METHOD);

        return be;
    }

    @Override
    public void deleteDidWeb(String did) {
        // request worked
    }

    @Override
    public void updateDidWeb(ParticipantDidUpdateRequestBE request) {

        if (request.getDid().equals(FAILING_UPDATE_DID)) {
            throw WebClientResponseException.create(500, "did update failed", null, null, null);
        }
        // request worked
    }

    @Override
    public String getDidDocument(String id) {

        return """
            {
              "@context": [
                "https://www.w3.org/ns/did/v1",
                "https://w3id.org/security/suites/jws-2020/v1"
              ],
              "id": \"""" + id + """
            ",
              "verificationMethod": [
                {
                  "@context": [
                    "https://w3c-ccg.github.io/lds-jws2020/contexts/v1/"
                  ],
                  "id": "did:web:example.com:participant:1234#JWK2020-Example",
                  "type": "JsonWebKey2020",
                  "controller": "did:web:example.com",
                  "publicKeyJwk": {
                    "kty": "RSA",
                    "n": "1234",
                    "e": "AQAB",
                    "alg": "PS256",
                    "x5u": "https://example.com/.well-known/cert.ss.pem"
                  }
                }
              ]
            }
            """;
    }
}
