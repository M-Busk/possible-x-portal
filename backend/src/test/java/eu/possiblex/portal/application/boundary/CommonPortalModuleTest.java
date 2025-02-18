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

import eu.possiblex.portal.application.configuration.AppConfigurer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommonPortalRestApiImpl.class)
@ContextConfiguration(classes = { AppConfigurer.class })
@TestPropertySource(properties = { "version.no = thisistheversion", "version.date = 21.03.2022",
    "fh.catalog.ui-url = http://localhost:8080" })
@Import(CommonPortalRestApiImpl.class)
class CommonPortalModuleTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVersionSucceeds() throws Exception {

        this.mockMvc.perform(get("/common/version").contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isOk()).andExpect(jsonPath("$.version").value("thisistheversion"))
            .andExpect(jsonPath("$.date").value("21.03.2022"));
    }

    @Test
    void getEnvironmentSucceeds() throws Exception {

        this.mockMvc.perform(get("/common/environment").contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isOk()).andExpect(jsonPath("$.catalogUiUrl").value("http://localhost:8080"));
    }

}
