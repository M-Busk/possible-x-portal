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

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.px.participants.PxParticipantExtensionCredentialSubject;
import eu.possiblex.portal.business.control.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationModuleTest.TestConfig.class })
@Transactional
@AutoConfigureMockMvc
class ParticipantRegistrationModuleTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerParticipantSuccess() throws Exception {

        CreateRegistrationRequestTO to = buildValidRegistrationRequest();

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    void registerParticipantRepetitionShouldTriggerConflict() throws Exception {

        CreateRegistrationRequestTO to = buildValidRegistrationRequest();

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isConflict());

    }

    @WithMockUser(username = "admin")
    @Test
    void registrationsShouldShowInList() throws Exception {

        CreateRegistrationRequestTO to = buildValidRegistrationRequest();

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        String anotherName = "anotherName";
        CreateRegistrationRequestTO anotherTo = buildValidRegistrationRequest();
        anotherTo.getParticipantCs().setName(anotherName);

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(anotherTo))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(get("/registration/request")).andDo(print()).andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains(to.getParticipantCs().getName()));
        assertTrue(content.contains(anotherTo.getParticipantCs().getName()));

    }

    @WithMockUser(username = "admin")
    @Test
    void registrationsDeletedEntriesShouldNotShowInList() throws Exception {

        CreateRegistrationRequestTO to = buildValidRegistrationRequest();

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        String anotherName = "another name";
        CreateRegistrationRequestTO anotherTo = buildValidRegistrationRequest();
        anotherTo.getParticipantCs().setName(anotherName);

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(anotherTo))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(delete("/registration/request/" + anotherTo.getParticipantCs().getName()).contentType(
            MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(get("/registration/request")).andDo(print()).andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertFalse(content.contains(anotherTo.getParticipantCs().getName()));
        assertTrue(content.contains(to.getParticipantCs().getName()));

    }

    @WithMockUser(username = "admin")
    @Test
    void registrationStateTransitionsSuccess() throws Exception {

        CreateRegistrationRequestTO to = buildValidRegistrationRequest();

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(post("/registration/request/" + to.getParticipantCs().getName() + "/reject").contentType(
            MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(
                delete("/registration/request/" + to.getParticipantCs().getName()).contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(post("/registration/request/" + to.getParticipantCs().getName() + "/accept").contentType(
            MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(get("/registration/request")).andDo(print()).andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("COMPLETED"));

    }

    private CreateRegistrationRequestTO buildValidRegistrationRequest() {

        GxVcard address = new GxVcard();
        address.setStreetAddress("Example Street 123");
        address.setLocality("Berlin");
        address.setPostalCode("12345");
        address.setCountryCode("DE");
        address.setCountrySubdivisionCode("DE-BE");
        GxLegalParticipantCredentialSubject participantCs = new GxLegalParticipantCredentialSubject();
        participantCs.setName("name");
        participantCs.setLegalAddress(address);
        participantCs.setHeadquarterAddress(address);
        GxLegalRegistrationNumberCredentialSubject registrationNumberCs = new GxLegalRegistrationNumberCredentialSubject();
        registrationNumberCs.setLeiCode("894500MQZ65CN32S9A66");
        PxParticipantExtensionCredentialSubject participantExtensionCs = new PxParticipantExtensionCredentialSubject();
        participantExtensionCs.setMailAddress("example@example.com");

        CreateRegistrationRequestTO to = new CreateRegistrationRequestTO();
        to.setParticipantCs(participantCs);
        to.setRegistrationNumberCs(registrationNumberCs);
        to.setParticipantExtensionCs(participantExtensionCs);

        return to;
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public TechnicalFhCatalogClient technicalFhCatalogClient() {

            return new TechnicalFhCatalogClientFake();
        }

        @Bean
        @Primary
        public DidWebServiceApiClient didWebServiceApiClient() {

            return new DidWebServiceApiClientFake();
        }

        @Bean
        @Primary
        public OmejdnConnectorApiClient omejdnConnectorApiClient() {

            return new OmejdnConnectorApiClientFake();
        }

        @Bean
        @Primary
        public SdCreationWizardApiClient sdCreationWizardApiClient() {

            return new SdCreationWizardApiClientFake();
        }

    }
}
