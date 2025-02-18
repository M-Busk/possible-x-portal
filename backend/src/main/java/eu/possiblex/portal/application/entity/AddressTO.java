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

package eu.possiblex.portal.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressTO {
    @Schema(description = "Country code as an ISO 3166-1 alpha2, alpha-3 or numeric format value", example = "DE")
    private String countryCode;

    @Schema(description = "Country subdivision code as an ISO 3166-2 format value", example = "DE-NI")
    private String countrySubdivisionCode;

    @Schema(description = "Street address", example = "Some Street 123")
    private String streetAddress;

    @Schema(description = "Locality", example = "Some City")
    private String locality;

    @Schema(description = "Postal Code", example = "12345")
    private String postalCode;
}
