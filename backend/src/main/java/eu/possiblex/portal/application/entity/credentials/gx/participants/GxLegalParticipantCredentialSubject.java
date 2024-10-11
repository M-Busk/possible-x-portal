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

package eu.possiblex.portal.application.entity.credentials.gx.participants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.possiblex.portal.application.entity.credentials.PojoCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.NodeKindIRITypeId;
import eu.possiblex.portal.application.entity.credentials.serialization.StringDeserializer;
import eu.possiblex.portal.application.entity.credentials.serialization.StringSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "type", "@context" }, allowGetters = true)
@NoArgsConstructor
@AllArgsConstructor
public class GxLegalParticipantCredentialSubject extends PojoCredentialSubject {

    @Getter(AccessLevel.NONE)
    public static final String TYPE_NAMESPACE = "gx";

    @Getter(AccessLevel.NONE)
    public static final String TYPE_CLASS = "LegalParticipant";

    @Getter(AccessLevel.NONE)
    public static final String TYPE = TYPE_NAMESPACE + ":" + TYPE_CLASS;

    @Getter(AccessLevel.NONE)
    public static final Map<String, String> CONTEXT = Map.of(TYPE_NAMESPACE,
        "https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#",
        "vcard", "http://www.w3.org/2006/vcard/ns#", "xsd", "http://www.w3.org/2001/XMLSchema#");

    // Tagus
    @NotNull
    @JsonProperty("gx:legalRegistrationNumber")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<NodeKindIRITypeId> legalRegistrationNumber; // will be gx:registrationNumber in Loire

    // Loire
    @NotNull
    @JsonProperty("gx:legalAddress")
    private GxVcard legalAddress; // contains Tagus gx:countrySubdivisionCode

    // Loire
    @NotNull
    @JsonProperty("gx:headquarterAddress")
    private GxVcard headquarterAddress; // contains Tagus gx:countrySubdivisionCode

    // Loire
    @JsonProperty("gx:name")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String name;

    // Loire
    @JsonProperty("gx:description")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String description;

    @JsonProperty("type")
    public String getType() {

        return TYPE;
    }

    @JsonProperty("@context")
    public Map<String, String> getContext() {

        return CONTEXT;
    }

}
