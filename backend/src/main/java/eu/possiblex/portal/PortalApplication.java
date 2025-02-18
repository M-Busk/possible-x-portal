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

package eu.possiblex.portal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "POSSIBLE-X Portal", description = "Service for handling participant registration requests within the POSSIBLE-X Dataspace"), tags = {
    @Tag(name = "Common", description = "API for accessing version information and environment variables of the POSSIBLE-X Portal"),
    @Tag(name = "Shapes", description = "API for accessing the Gaia-X and POSSIBLE-X shapes for participant registration request creation"),
    @Tag(name = "Registration", description = "API for creating and managing participant registration requests") })

@SpringBootApplication
public class PortalApplication {

    public static void main(String[] args) {

        SpringApplication.run(PortalApplication.class, args);
    }

}
