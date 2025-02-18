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

package eu.possiblex.portal.application.boundary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/shapes")
public interface ParticipantShapeRestApi {
    @Operation(summary = "Get the Gaia-X legal participant shape", tags = {
        "Shapes" }, description = "Get the Gaia-X legal participant shape.", responses = {
        @ApiResponse(content = @Content(schema = @Schema(description = "Gaia-X legal participant shape as JSON string", example = "{ \"prefixList\": [], \"shapes\": [] }"))) })
    @GetMapping("/gx/legalparticipant")
    String getGxLegalParticipantShape();

    @Operation(summary = "Get the Gaia-X legal registration number shape", tags = {
        "Shapes" }, description = "Get the Gaia-X legal registration number shape.", responses = {
        @ApiResponse(content = @Content(schema = @Schema(description = "Gaia-X legal registration number shape as JSON string", example = "{ \"prefixList\": [], \"shapes\": [] }"))) })
    @GetMapping("/gx/legalregistrationnumber")
    String getGxLegalRegistrationNumberShape();

    @Operation(summary = "Get the Possible-X participant extension shape", tags = {
        "Shapes" }, description = "Get the Possible-X participant extension shape.", responses = {
        @ApiResponse(content = @Content(schema = @Schema(description = "Possible-X participant extension shape as JSON string", example = "{ \"prefixList\": [], \"shapes\": [] }"))) })
    @GetMapping("/px/participantextension")
    String getPxParticipantExtension();
}
