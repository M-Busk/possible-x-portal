package eu.possiblex.portal.business.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class TechnicalFhCatalogClientFake implements TechnicalFhCatalogClient {

    public static final String VALID_ID = "some-id";

    public static final String MISSING_PARTICIPANT_ID = "missing";

    public static final String BAD_COMMUNICATION_ID = "badcommunication";

    public static final String BAD_COMPLIANCE_ID = "badcompliance";

    public static final String BAD_PARSING_ID = "badparsing";

    @Override
    public FhCatalogIdResponse addParticipantToFhCatalog(PxExtendedLegalParticipantCredentialSubject participantCs,
        String verificationMethod) {

        if (participantCs.getId().equals(BAD_COMMUNICATION_ID)) {
            throw WebClientResponseException.create(500, "Some other error", null, null, null);
        }
        if (participantCs.getId().equals(BAD_COMPLIANCE_ID)) {
            String jsonBody = "{\"error\":\"compliance error\"}";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            WebClientResponseException responseException = WebClientResponseException.create(422, "compliance error",
                headers, jsonBody.getBytes(), null);

            responseException.setBodyDecodeFunction(bytes -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readTree(jsonBody);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to decode JSON", e);
                }
            });

            throw responseException;
        }

        return new FhCatalogIdResponse(participantCs.getId());
    }

    @Override
    public String getParticipantFromCatalog(String participantId) {

        switch (participantId) {
            case MISSING_PARTICIPANT_ID ->
                throw WebClientResponseException.create(404, "Participant not found", null, null, null);
            case BAD_COMMUNICATION_ID ->
                throw WebClientResponseException.create(500, "Some other error", null, null, null);
            case BAD_PARSING_ID -> {
                return "invalid json";
            }
            default -> {
                return """
                    {
                        "@graph": [
                            {
                                "@id": "_:b0",
                                "gx:countrySubdivisionCode": "DE-BE",
                                "vcard:locality": "Berlin",
                                "vcard:street-address": "Example Street 123",
                                "vcard:postal-code": "12345",
                                "gx:countryCode": "DE"
                            },
                            {
                                "@id": "_:b1",
                                "gx:leiCode": "894500MQZ65CN32S9A66"
                            },
                            {
                                "@id": \"""" + participantId + """
                    ",
                                "http://w3id.org/gaia-x/possible-x#mailAddress": "example@example.com",
                                "gx:legalRegistrationNumber": {
                                    "@id": "_:b1"
                                },
                                "@type": [
                                    "http://w3id.org/gaia-x/possible-x#PossibleXLegalParticipantExtension",
                                    "gx:LegalParticipant"
                                ],
                                "schema:name": "Example Org",
                                "gx:legalAddress": {
                                    "@id": "_:b0"
                                },
                                "gx:headquarterAddress": {
                                    "@id": "_:b2"
                                },
                                "schema:description": "Some description"
                            },
                            {
                                "@id": "_:b2",
                                "gx:countrySubdivisionCode": "DE-BE",
                                "vcard:locality": "Berlin",
                                "vcard:street-address": "Example Street 123",
                                "vcard:postal-code": "12345",
                                "gx:countryCode": "DE"
                            }
                        ],
                        "@context": {
                            "schema": "https://schema.org/",
                            "gx": "https://w3id.org/gaia-x/development#",
                            "vcard": "http://www.w3.org/2006/vcard/ns#",
                            "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                        }
                    }
                    """;
            }
        }
    }

    @Override
    public void deleteParticipantFromCatalog(String participantId) {

        if (participantId.equals(MISSING_PARTICIPANT_ID)) {
            throw WebClientResponseException.create(404, "Participant not found", null, null, null);
        }

        if (participantId.equals(BAD_COMMUNICATION_ID)) {
            throw WebClientResponseException.create(500, "Some other error", null, null, null);
        }

        // success
    }
}
