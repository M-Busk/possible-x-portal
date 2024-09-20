package eu.possiblex.portal.business.control;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationServiceTest.TestConfig.class, ParticipantRegistrationServiceImpl.class })
class ParticipantRegistrationServiceTest {

    // Test-specific configuration to provide mocks
    @TestConfiguration
    static class TestConfig {
    }

    @Test
    void registerParticipant() {
        // TODO add proper test
        assertTrue(true);
    }
}
