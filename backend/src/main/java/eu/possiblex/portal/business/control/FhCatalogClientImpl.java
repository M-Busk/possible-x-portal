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

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.document.JsonDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.CatalogCommunicationException;
import eu.possiblex.portal.business.entity.exception.CatalogParsingException;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import eu.possiblex.portal.utilities.LogUtils;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FhCatalogClientImpl implements FhCatalogClient {

    private final TechnicalFhCatalogClient technicalFhCatalogClient;

    private final ObjectMapper objectMapper;

    public FhCatalogClientImpl(@Autowired TechnicalFhCatalogClient technicalFhCatalogClient,
        @Autowired ObjectMapper objectMapper) {

        this.technicalFhCatalogClient = technicalFhCatalogClient;
        this.objectMapper = objectMapper;
    }

    private static JsonDocument getFrameByType(List<String> type, Map<String, String> context) {

        JsonObjectBuilder contextBuilder = Json.createObjectBuilder();
        context.forEach(contextBuilder::add);

        JsonArrayBuilder typeArrayBuilder = Json.createArrayBuilder();
        type.forEach(typeArrayBuilder::add);

        return JsonDocument.of(
            Json.createObjectBuilder().add("@context", contextBuilder.build()).add("@type", typeArrayBuilder.build())
                .build());
    }

    @Override
    public FhCatalogIdResponse addParticipantToCatalog(PxExtendedLegalParticipantCredentialSubject cs) {

        String verMethod = cs.getId() + "#JWK2020-PossibleLetsEncrypt";
        log.info("sending to catalog: {} with verMethod {}", LogUtils.serializeObjectToJson(cs), verMethod);

        FhCatalogIdResponse catalogParticipantId;
        try {
            catalogParticipantId = technicalFhCatalogClient.addParticipantToFhCatalog(cs, verMethod);
        } catch (Exception e) {
            throw buildCatalogCommunicationException(e);
        }
        log.info("got participant id: {}", catalogParticipantId.getId());
        return catalogParticipantId;
    }

    @Override
    public PxExtendedLegalParticipantCredentialSubject getParticipantFromCatalog(String participantId) {

        log.info("fetching participant for fh catalog ID {}", participantId);
        String participantJsonContent;
        try {
            participantJsonContent = technicalFhCatalogClient.getParticipantFromCatalog(participantId);
        } catch (Exception e) {
            throw buildCatalogCommunicationException(e);
        }
        log.info("answer for fh catalog ID {}: {}", participantId, participantJsonContent);

        try {
            JsonDocument input = JsonDocument.of(new StringReader(participantJsonContent));
            JsonDocument participantFrame = getFrameByType(PxExtendedLegalParticipantCredentialSubject.TYPE,
                PxExtendedLegalParticipantCredentialSubject.CONTEXT);
            JsonObject framedParticipant = JsonLd.frame(input, participantFrame).get();

            return objectMapper.readValue(framedParticipant.toString(),
                PxExtendedLegalParticipantCredentialSubject.class);
        } catch (Exception e) {
            throw new CatalogParsingException("failed to parse fh catalog participant json: " + participantJsonContent,
                e);
        }
    }

    @Override
    public void deleteParticipantFromCatalog(String participantId) {

        log.info("deleting participant from fh catalog with ID {}", participantId);
        try {
            technicalFhCatalogClient.deleteParticipantFromCatalog(participantId);
        } catch (Exception e) {
            throw buildCatalogCommunicationException(e);
        }
    }

    private RuntimeException buildCatalogCommunicationException(Exception e) {

        log.warn("error during communication with catalog", e);
        if (e instanceof WebClientResponseException responseException) {
            if (responseException.getStatusCode().value() == 404) {
                return new ParticipantNotFoundException("no FH Catalog participant found with given id");
            }
            if (responseException.getStatusCode().value() == 422) {
                JsonNode error = responseException.getResponseBodyAs(JsonNode.class);
                if (error != null && error.get("error") != null) {
                    return new ParticipantComplianceException(error.get("error").textValue(), e);
                }
                return new ParticipantComplianceException("Unknown catalog processing exception", e);
            }
            return new CatalogCommunicationException("Catalog returned bad response", responseException);
        } else {
            return new CatalogCommunicationException("Unexpected error during catalog request", e);
        }

    }
}
