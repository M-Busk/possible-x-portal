/*
 *  Copyright 2024 Dataport. All rights reserved. Developed as part of the MERLOT project.
 *  Copyright 2024-2025 Dataport. All rights reserved. Extended as part of the POSSIBLE project.
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

package eu.possiblex.portal.application.entity.credentials.gx.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.possiblex.portal.application.entity.credentials.PojoCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.serialization.StringDeserializer;
import eu.possiblex.portal.application.entity.credentials.serialization.StringSerializer;
import eu.possiblex.portal.application.entity.credentials.validation.AtLeastOneRegistrationNumberNotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "type", "@context" }, allowGetters = true)
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneRegistrationNumberNotEmpty(message = "At least one registration number type must be provided")
public class GxLegalRegistrationNumberCredentialSubject extends PojoCredentialSubject {

    @Getter(AccessLevel.NONE)
    public static final String TYPE_NAMESPACE = "gx";

    @Getter(AccessLevel.NONE)
    public static final String TYPE_CLASS = "legalRegistrationNumber";

    @Getter(AccessLevel.NONE)
    public static final String TYPE = TYPE_NAMESPACE + ":" + TYPE_CLASS;

    @Getter(AccessLevel.NONE)
    public static final Map<String, String> CONTEXT = Map.of(TYPE_NAMESPACE,
        "https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#", "xsd",
        "http://www.w3.org/2001/XMLSchema#");

    @Schema(description = "EORI", example = "DE123456789012345")
    @JsonProperty("gx:EORI")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String eori;

    @Schema(description = "VAT ID", example = "DE269448547")
    @JsonProperty("gx:vatID")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String vatID;

    @Schema(description = "LEI Code", example = "391200RT75XV0TG47X87")
    @JsonProperty("gx:leiCode")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String leiCode;


    @Schema(description = "JSON-LD type", example = "gx:legalRegistrationNumber")
    @JsonProperty("type")
    public String getType() {

        return TYPE;
    }

    @Schema(description = "JSON-LD context", example = "{\"gx\": \"https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#\"}")
    @JsonProperty("@context")
    public Map<String, String> getContext() {

        return CONTEXT;
    }

}