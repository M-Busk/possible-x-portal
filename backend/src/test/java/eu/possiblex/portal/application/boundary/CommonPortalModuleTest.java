package eu.possiblex.portal.application.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import eu.possiblex.portal.application.configuration.AppConfigurer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommonPortalRestApiImpl.class)
@ContextConfiguration(classes = { AppConfigurer.class })
@TestPropertySource(properties = {"version.no = thisistheversion", "version.date = 21.03.2022"})
@Import(CommonPortalRestApiImpl.class)
public class CommonPortalModuleTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVersionSucceeds() throws Exception {
        this.mockMvc.perform(get("/common/version").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("thisistheversion"))
                .andExpect(jsonPath("$.date").value("21.03.2022"));
    }

}
