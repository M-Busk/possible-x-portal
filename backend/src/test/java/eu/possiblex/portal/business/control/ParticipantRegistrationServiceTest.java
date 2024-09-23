package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationServiceTest.TestConfig.class,
    ParticipantRegistrationServiceImpl.class })
class ParticipantRegistrationServiceTest {

    @Autowired
    private ParticipantRegistrationService participantRegistrationService;

    @Test
    void registerParticipant() {
        // TODO add proper test
        participantRegistrationService.registerParticipant(PossibleParticipantBE.builder().build());
        assertTrue(true);
    }

    // Test-specific configuration to provide mocks
    @TestConfiguration
    static class TestConfig {
    }
}
