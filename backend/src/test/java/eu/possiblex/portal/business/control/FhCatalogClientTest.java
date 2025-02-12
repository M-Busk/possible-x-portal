package eu.possiblex.portal.business.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.CatalogCommunicationException;
import eu.possiblex.portal.business.entity.exception.CatalogParsingException;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { FhCatalogClientTest.TestConfig.class, FhCatalogClientImpl.class })
class FhCatalogClientTest {

    @Autowired
    private FhCatalogClient sut;

    @Test
    void addParticipantToCatalogSuccess() {

        PxExtendedLegalParticipantCredentialSubject cs = new PxExtendedLegalParticipantCredentialSubject();
        cs.setId(TechnicalFhCatalogClientFake.VALID_ID);
        FhCatalogIdResponse idResponse = sut.addParticipantToCatalog(cs);
        assertNotNull(idResponse);
    }

    @Test
    void addParticipantToCatalogCommunicationFailed() {

        PxExtendedLegalParticipantCredentialSubject cs = new PxExtendedLegalParticipantCredentialSubject();
        cs.setId(TechnicalFhCatalogClientFake.BAD_COMMUNICATION_ID);

        assertThrows(CatalogCommunicationException.class, () -> sut.addParticipantToCatalog(cs));
    }

    @Test
    void addParticipantToCatalogComplianceFailed() {

        PxExtendedLegalParticipantCredentialSubject cs = new PxExtendedLegalParticipantCredentialSubject();
        cs.setId(TechnicalFhCatalogClientFake.BAD_COMPLIANCE_ID);

        assertThrows(ParticipantComplianceException.class, () -> sut.addParticipantToCatalog(cs));
    }

    @Test
    void getParticipantFromCatalogSuccess() {

        PxExtendedLegalParticipantCredentialSubject cs = sut.getParticipantFromCatalog(
            TechnicalFhCatalogClientFake.VALID_ID);
        assertNotNull(cs);
        assertEquals(TechnicalFhCatalogClientFake.VALID_ID, cs.getId());
    }

    @Test
    void getParticipantFromCatalogNotFound() {

        assertThrows(ParticipantNotFoundException.class,
            () -> sut.getParticipantFromCatalog(TechnicalFhCatalogClientFake.MISSING_PARTICIPANT_ID));
    }

    @Test
    void getParticipantFromCatalogParsingFailed() {

        assertThrows(CatalogParsingException.class,
            () -> sut.getParticipantFromCatalog(TechnicalFhCatalogClientFake.BAD_PARSING_ID));
    }

    @Test
    void getParticipantFromCatalogCommunicationFailed() {

        assertThrows(CatalogCommunicationException.class,
            () -> sut.getParticipantFromCatalog(TechnicalFhCatalogClientFake.BAD_COMMUNICATION_ID));
    }

    @Test
    void deleteParticipantFromCatalog() {

        assertDoesNotThrow(() -> sut.deleteParticipantFromCatalog(TechnicalFhCatalogClientFake.VALID_ID));
    }

    @Test
    void deleteParticipantFromCatalogNotFound() {

        assertThrows(ParticipantNotFoundException.class,
            () -> sut.deleteParticipantFromCatalog(TechnicalFhCatalogClientFake.MISSING_PARTICIPANT_ID));
    }

    @Test
    void deleteParticipantFromCatalogCommunicationFailed() {

        assertThrows(CatalogCommunicationException.class,
            () -> sut.deleteParticipantFromCatalog(TechnicalFhCatalogClientFake.BAD_COMMUNICATION_ID));
    }

    // Test-specific configuration to provide mocks
    @TestConfiguration
    static class TestConfig {
        @Bean
        public TechnicalFhCatalogClient technicalFhCatalogClient() {

            return Mockito.spy(new TechnicalFhCatalogClientFake());
        }

        @Bean
        public ObjectMapper objectMapper() {

            return new ObjectMapper();
        }

    }
}
