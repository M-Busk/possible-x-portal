package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAOFake;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationServiceTest.TestConfig.class,
    ParticipantRegistrationServiceImpl.class })
class ParticipantRegistrationServiceTest {
    @Autowired
    private ParticipantRegistrationRequestDAO participantRegistrationRequestDao;

    @Autowired
    private OmejdnConnectorApiClient omejdnConnectorApiClient;

    @Autowired
    private DidWebServiceApiClient didWebServiceApiClient;

    @Autowired
    private ParticipantRegistrationService participantRegistrationService;

    @Test
    void registerParticipant() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipantCs();
        ParticipantMetadataBE metadata = ParticipantMetadataBE.builder().emailAddress("example@address.com").build();
        participantRegistrationService.registerParticipant(participant, metadata);
        verify(participantRegistrationRequestDao).saveParticipantRegistrationRequest(any(), any());
    }

    @Test
    void getAllParticipantRegistrationRequests() {

        List<RegistrationRequestEntryTO> list = participantRegistrationService.getAllParticipantRegistrationRequests();
        assertEquals(1, list.size());

        verify(participantRegistrationRequestDao).getAllParticipantRegistrationRequests();
    }

    @Test
    void acceptRegistrationRequest() {

        participantRegistrationService.acceptRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).acceptRegistrationRequest("validId");
        verify(omejdnConnectorApiClient).addConnector(new OmejdnConnectorCertificateRequest("validId"));
        verify(didWebServiceApiClient).generateDidWeb(new ParticipantDidCreateRequestBE("validId"));
        verify(participantRegistrationRequestDao).completeRegistrationRequest("validId");
    }

    @Test
    void rejectRegistrationRequest() {

        participantRegistrationService.rejectRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).rejectRegistrationRequest("validId");
    }

    @Test
    void deleteRegistrationRequest() {

        participantRegistrationService.deleteRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).deleteRegistrationRequest("validId");
    }

    private PxExtendedLegalParticipantCredentialSubject getParticipantCs() {

        ParticipantRegistrationRequestBE be = ParticipantRegistrationRequestDAOFake.getExampleParticipant();

        return PxExtendedLegalParticipantCredentialSubject.builder().id("validId")
            .legalRegistrationNumber(be.getLegalRegistrationNumber()).headquarterAddress(be.getHeadquarterAddress())
            .legalAddress(be.getLegalAddress()).name(be.getName()).description(be.getDescription()).build();
    }

    // Test-specific configuration to provide mocks
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ParticipantRegistrationEntityMapper participantRegistrationEntityMapper() {

            return Mappers.getMapper(ParticipantRegistrationEntityMapper.class);
        }

        @Bean
        public ParticipantRegistrationServiceMapper participantRegistrationServiceMapper() {

            return Mappers.getMapper(ParticipantRegistrationServiceMapper.class);
        }

        @Bean
        public OmejdnConnectorApiClient dapsConnectorApiClient() {

            return Mockito.spy(new OmejdnConnectorApiClientFake());
        }

        @Bean
        public DidWebServiceApiClient didWebServiceApiClient() {

            return Mockito.spy(new DidWebServiceApiClientFake());
        }

        @Bean
        public ParticipantRegistrationRequestDAO participantRegistrationRequestDAO() {

            return Mockito.spy(new ParticipantRegistrationRequestDAOFake());
        }
    }
}
