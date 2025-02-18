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
import eu.possiblex.portal.business.control.SdCreationWizardApiService;
import eu.possiblex.portal.business.control.SdCreationWizardApiServiceFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipantShapeRestApiImpl.class)
@ContextConfiguration(classes = { ParticipantShapeRestApiTest.TestConfig.class, ParticipantShapeRestApiImpl.class,
    AppConfigurer.class })
class ParticipantShapeRestApiTest {
    @Autowired
    SdCreationWizardApiService sdCreationWizardApiService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        reset(sdCreationWizardApiService);
    }

    @Test
    void getGxLegalParticipantShape() throws Exception {

        this.mockMvc.perform(get("/shapes/gx/legalparticipant").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.someKey").value("someValue"));
    }

    @Test
    void getGxLegalRegistrationNumberShape() throws Exception {

        this.mockMvc.perform(get("/shapes/gx/legalregistrationnumber").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.someKey").value("someValue"));
    }

    @Test
    void getPxParticipantExtensionShape() throws Exception {

        this.mockMvc.perform(get("/shapes/px/participantextension").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.someKey").value("someValue"));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public SdCreationWizardApiService sdCreationWizardApiService() {

            return Mockito.spy(new SdCreationWizardApiServiceFake());
        }
    }
}