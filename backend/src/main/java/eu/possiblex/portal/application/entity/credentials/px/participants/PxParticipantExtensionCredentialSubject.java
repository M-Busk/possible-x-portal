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

package eu.possiblex.portal.application.entity.credentials.px.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.possiblex.portal.application.entity.credentials.PojoCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.serialization.StringDeserializer;
import eu.possiblex.portal.application.entity.credentials.serialization.StringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "type", "@context" }, allowGetters = true)
@NoArgsConstructor
@AllArgsConstructor
public class PxParticipantExtensionCredentialSubject extends PojoCredentialSubject {
    @Getter(AccessLevel.NONE)
    public static final String TYPE_NAMESPACE = "px";

    @Getter(AccessLevel.NONE)
    public static final String TYPE_CLASS = "PossibleXLegalParticipantExtension";

    @Getter(AccessLevel.NONE)
    public static final String TYPE = TYPE_NAMESPACE + ":" + TYPE_CLASS;

    @Getter(AccessLevel.NONE)
    public static final Map<String, String> CONTEXT = Map.of(TYPE_NAMESPACE, "http://w3id.org/gaia-x/possible-x#",
        "vcard", "http://www.w3.org/2006/vcard/ns#", "xsd", "http://www.w3.org/2001/XMLSchema#");

    @Schema(description = "Email address", example = "contact@someorg.com")
    @NotBlank(message = "Mail address is needed")
    @JsonProperty("px:mailAddress")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    @Pattern(regexp = "^((?!\\.)[\\w\\-.]*[^.])(@[\\w-]+)(\\.\\w+(\\.\\w+)?\\w)$", message = "Mail address must be a valid email address")
    private String mailAddress;

    @Schema(description = "JSON-LD type", example = "px:PossibleXLegalParticipantExtension")
    @JsonProperty("type")
    public String getType() {

        return TYPE;
    }

    @Schema(description = "JSON-LD context", example = "{\"px\": \"http://w3id.org/gaia-x/possible-x#\"}")
    @JsonProperty("@context")
    public Map<String, String> getContext() {

        return CONTEXT;
    }
}
