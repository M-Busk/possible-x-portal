package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.RegistrationRequestTO;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import eu.possiblex.portal.business.control.ParticipantRegistrationServiceMock;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipantRegistrationRestApiImpl.class)
@ContextConfiguration(classes = { ParticipantRegistrationRestApiTest.TestConfig.class,
    ParticipantRegistrationRestApiImpl.class })
class ParticipantRegistrationRestApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParticipantRegistrationService participantRegistrationService;

    @Test
    @Disabled
        // TODO enable once implemented
    void registerParticipant() throws Exception {

        RegistrationRequestTO to = new RegistrationRequestTO();
        reset(participantRegistrationService);
        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ParticipantRegistrationService participantRegistrationService() {

            return Mockito.spy(new ParticipantRegistrationServiceMock());
        }
    }

}
