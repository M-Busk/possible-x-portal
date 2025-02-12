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