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

import eu.possiblex.portal.application.entity.EnvironmentTO;
import eu.possiblex.portal.application.entity.VersionTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/common")
public interface CommonPortalRestApi {
    @Operation(summary = "Get the version of the portal", tags = {
        "Common" }, description = "Get the version of the portal.")
    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    VersionTO getVersion();

    @Operation(summary = "Get the environment of the portal", tags = {
        "Common" }, description = "Get the environment of the portal.")
    @GetMapping(value = "/environment", produces = MediaType.APPLICATION_JSON_VALUE)
    EnvironmentTO getEnvironment();
}
