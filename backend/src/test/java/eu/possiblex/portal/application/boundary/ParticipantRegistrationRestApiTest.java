package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.control.ParticipantRegistrationRestApiMapper;
import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import eu.possiblex.portal.business.control.ParticipantRegistrationServiceFake;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void registerParticipant() throws Exception {

        CreateRegistrationRequestTO to = new CreateRegistrationRequestTO();
        reset(participantRegistrationService);
        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).registerParticipant(any(), any());
    }

    @Test
    void getAllRegistrationRequests() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(get("/registration/request")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).getAllParticipantRegistrationRequests();
    }

    @Test
    void acceptRegistrationRequest() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(post("/registration/request/validId/accept")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).acceptRegistrationRequest("validId");
    }

    @Test
    void rejectRegistrationRequest() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(post("/registration/request/validId/reject")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).rejectRegistrationRequest("validId");
    }

    @Test
    void deleteRegistrationRequest() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(delete("/registration/request/validId")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).deleteRegistrationRequest("validId");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ParticipantRegistrationService participantRegistrationService() {

            return Mockito.spy(new ParticipantRegistrationServiceFake());
        }

        @Bean
        public ParticipantRegistrationRestApiMapper participantCredentialMapper() {

            return Mappers.getMapper(ParticipantRegistrationRestApiMapper.class);
        }

    }

}
