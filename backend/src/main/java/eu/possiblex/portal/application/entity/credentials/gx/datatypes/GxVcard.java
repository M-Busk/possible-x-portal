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

package eu.possiblex.portal.application.entity.credentials.gx.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.possiblex.portal.application.entity.credentials.serialization.StringDeserializer;
import eu.possiblex.portal.application.entity.credentials.serialization.StringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GxVcard {

    @Schema(description = "Country code as an ISO 3166-1 alpha2, alpha-3 or numeric format value", example = "DE")
    @JsonProperty("gx:countryCode")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    @NotBlank(message = "Country code is needed")
    @Pattern(regexp = "^([A-Z]{2}|[A-Z]{3}|\\d{3})$", message = "An ISO 3166-1 alpha2, alpha-3 or numeric format value is expected.")
    private String countryCode;

    @Schema(description = "Country subdivision code as an ISO 3166-2 format value", example = "DE-NI")
    @JsonProperty("gx:countrySubdivisionCode")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    @NotBlank(message = "Country subdivision code is needed")
    @Pattern(regexp = "^[A-Z]{2}-[A-Z0-9]{1,3}$", message = "An ISO 3166-2 format value is expected.")
    private String countrySubdivisionCode;

    @Schema(description = "Street address", example = "Some Street 123")
    @JsonProperty("vcard:street-address")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String streetAddress;

    @Schema(description = "Locality", example = "Some City")
    @JsonProperty("vcard:locality")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String locality;

    @Schema(description = "Postal Code", example = "12345")
    @JsonProperty("vcard:postal-code")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String postalCode;
}