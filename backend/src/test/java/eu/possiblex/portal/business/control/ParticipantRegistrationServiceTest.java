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

package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.GxNestedLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestConflictException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestProcessingException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAOFake;
import eu.possiblex.portal.testutilities.ResultCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    private FhCatalogClient fhCatalogClient;

    @Autowired
    private ParticipantRegistrationService sut;

    @BeforeEach
    void setUp() {

        reset(participantRegistrationRequestDao);
        reset(omejdnConnectorApiClient);
        reset(didWebServiceApiClient);
        reset(fhCatalogClient);
    }

    @Test
    void registerParticipantSuccess() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipantCs();
        sut.registerParticipant(participant);
        verify(participantRegistrationRequestDao).saveParticipantRegistrationRequest(any());
    }

    @Test
    void registerParticipantAlreadyExists() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipantCs();
        participant.setName(ParticipantRegistrationRequestDAOFake.EXISTING_NAME);

        assertThrows(RegistrationRequestConflictException.class, () -> sut.registerParticipant(participant));

        verify(participantRegistrationRequestDao).getRegistrationRequestByName(
            ParticipantRegistrationRequestDAOFake.EXISTING_NAME);
        verify(participantRegistrationRequestDao, times(0)).saveParticipantRegistrationRequest(any());
    }

    @Test
    void getParticipantRegistrationRequestsSingleItem() {

        Page<RegistrationRequestEntryTO> to = sut.getParticipantRegistrationRequests(PageRequest.of(0, 1));
        assertEquals(1, to.getContent().size());

        verify(participantRegistrationRequestDao).getRegistrationRequests(any());
    }

    @Test
    void getParticipantRegistrationRequestsFullPage() {

        Page<RegistrationRequestEntryTO> to = sut.getParticipantRegistrationRequests(PageRequest.of(0, 10));
        assertTrue(to.getContent().size() > 1);

        verify(participantRegistrationRequestDao).getRegistrationRequests(any());
    }

    @Test
    void getParticipantRegistrationRequestByDidSuccess() {

        RegistrationRequestEntryTO entry = sut.getParticipantRegistrationRequestByDid(
            ParticipantRegistrationRequestDAOFake.EXISTING_DID);
        assertNotNull(entry);

        verify(participantRegistrationRequestDao).getRegistrationRequestByDid(
            ParticipantRegistrationRequestDAOFake.EXISTING_DID);
    }

    @Test
    void getParticipantRegistrationRequestByDidUnknown() {

        assertThrows(ParticipantNotFoundException.class,
            () -> sut.getParticipantRegistrationRequestByDid(ParticipantRegistrationRequestDAOFake.NON_EXISTING_DID));
        verify(participantRegistrationRequestDao).getRegistrationRequestByDid(
            ParticipantRegistrationRequestDAOFake.NON_EXISTING_DID);
    }

    @Test
    void acceptRegistrationRequestSuccess() {

        ArgumentCaptor<OmejdnConnectorCertificateBE> certificateCaptor = ArgumentCaptor.forClass(
            OmejdnConnectorCertificateBE.class);

        sut.acceptRegistrationRequest(ParticipantRegistrationRequestDAOFake.EXISTING_NAME);
        verify(participantRegistrationRequestDao).acceptRegistrationRequest(any());
        verify(didWebServiceApiClient).generateDidWeb(any());
        verify(fhCatalogClient).addParticipantToCatalog(any());
        verify(omejdnConnectorApiClient).addConnector(any());
        verify(didWebServiceApiClient).updateDidWeb(any());
        verify(participantRegistrationRequestDao).completeRegistrationRequest(any(), any(), any(),
            certificateCaptor.capture());

        OmejdnConnectorCertificateBE certificate = certificateCaptor.getValue();
        assertNotNull(certificate.getKeystore());
        assertNotNull(certificate.getClientId());
        assertNotNull(certificate.getPassword());
    }

    @Test
    void acceptRegistrationRequestDidCreationFails() {

        ResultCaptor<ParticipantDidBE> didBEResultCaptor = new ResultCaptor<>();
        doAnswer(didBEResultCaptor).when(didWebServiceApiClient).generateDidWeb(any());

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.acceptRegistrationRequest(DidWebServiceApiClientFake.FAILING_REQUEST_SUBJECT));

        verify(participantRegistrationRequestDao).acceptRegistrationRequest(any());
        verify(didWebServiceApiClient).generateDidWeb(any());

        verify(fhCatalogClient, times(0)).addParticipantToCatalog(any());
        verify(omejdnConnectorApiClient, times(0)).addConnector(any());
        verify(didWebServiceApiClient, times(0)).updateDidWeb(any());
        verify(participantRegistrationRequestDao, times(0)).completeRegistrationRequest(any(), any(), any(), any());
    }

    @Test
    void acceptRegistrationRequestCatalogFails() {

        ResultCaptor<ParticipantDidBE> didBEResultCaptor = new ResultCaptor<>();
        doAnswer(didBEResultCaptor).when(didWebServiceApiClient).generateDidWeb(any());

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.acceptRegistrationRequest(FhCatalogClientFake.FAILING_PARTICIPANT_ID));

        verify(participantRegistrationRequestDao).acceptRegistrationRequest(any());
        verify(didWebServiceApiClient).generateDidWeb(any());
        verify(fhCatalogClient).addParticipantToCatalog(any());

        verify(omejdnConnectorApiClient, times(0)).addConnector(any());
        verify(didWebServiceApiClient, times(0)).updateDidWeb(any());
        verify(participantRegistrationRequestDao, times(0)).completeRegistrationRequest(any(), any(), any(), any());

        verify(didWebServiceApiClient).deleteDidWeb(didBEResultCaptor.getResult().getDid());
    }

    @Test
    void acceptRegistrationRequestBadCompliance() {

        ResultCaptor<ParticipantDidBE> didBEResultCaptor = new ResultCaptor<>();
        doAnswer(didBEResultCaptor).when(didWebServiceApiClient).generateDidWeb(any());

        assertThrows(ParticipantComplianceException.class,
            () -> sut.acceptRegistrationRequest(FhCatalogClientFake.BAD_COMPLIANCE_PARTICIPANT_ID));

        verify(participantRegistrationRequestDao).acceptRegistrationRequest(any());
        verify(didWebServiceApiClient).generateDidWeb(any());
        verify(fhCatalogClient).addParticipantToCatalog(any());

        verify(omejdnConnectorApiClient, times(0)).addConnector(any());
        verify(didWebServiceApiClient, times(0)).updateDidWeb(any());
        verify(participantRegistrationRequestDao, times(0)).completeRegistrationRequest(any(), any(), any(), any());

        verify(didWebServiceApiClient).deleteDidWeb(didBEResultCaptor.getResult().getDid());
    }

    @Test
    void acceptRegistrationRequestDapsFails() {

        ResultCaptor<ParticipantDidBE> didBEResultCaptor = new ResultCaptor<>();
        doAnswer(didBEResultCaptor).when(didWebServiceApiClient).generateDidWeb(any());
        ResultCaptor<FhCatalogIdResponse> catalogIdResponseResultCaptor = new ResultCaptor<>();
        doAnswer(catalogIdResponseResultCaptor).when(fhCatalogClient).addParticipantToCatalog(any());

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.acceptRegistrationRequest(OmejdnConnectorApiClientFake.FAILING_NAME));

        verify(participantRegistrationRequestDao).acceptRegistrationRequest(any());
        verify(didWebServiceApiClient).generateDidWeb(any());
        verify(fhCatalogClient).addParticipantToCatalog(any());
        verify(omejdnConnectorApiClient).addConnector(any());

        verify(didWebServiceApiClient, times(0)).updateDidWeb(any());
        verify(participantRegistrationRequestDao, times(0)).completeRegistrationRequest(any(), any(), any(), any());

        verify(didWebServiceApiClient).deleteDidWeb(didBEResultCaptor.getResult().getDid());
        verify(fhCatalogClient).deleteParticipantFromCatalog(catalogIdResponseResultCaptor.getResult().getId());
    }

    @Test
    void acceptRegistrationRequestDidUpdateFails() {

        ResultCaptor<ParticipantDidBE> didBEResultCaptor = new ResultCaptor<>();
        doAnswer(didBEResultCaptor).when(didWebServiceApiClient).generateDidWeb(any());
        ResultCaptor<FhCatalogIdResponse> catalogIdResponseResultCaptor = new ResultCaptor<>();
        doAnswer(catalogIdResponseResultCaptor).when(fhCatalogClient).addParticipantToCatalog(any());
        ResultCaptor<OmejdnConnectorCertificateBE> certificateBEResultCaptor = new ResultCaptor<>();
        doAnswer(certificateBEResultCaptor).when(omejdnConnectorApiClient).addConnector(any());

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.acceptRegistrationRequest(DidWebServiceApiClientFake.FAILING_UPDATE_DID));

        verify(participantRegistrationRequestDao).acceptRegistrationRequest(any());
        verify(didWebServiceApiClient).generateDidWeb(any());
        verify(fhCatalogClient).addParticipantToCatalog(any());
        verify(omejdnConnectorApiClient).addConnector(any());
        verify(didWebServiceApiClient).updateDidWeb(any());

        verify(participantRegistrationRequestDao, times(0)).completeRegistrationRequest(any(), any(), any(), any());

        verify(didWebServiceApiClient).deleteDidWeb(didBEResultCaptor.getResult().getDid());
        verify(fhCatalogClient).deleteParticipantFromCatalog(catalogIdResponseResultCaptor.getResult().getId());
        verify(omejdnConnectorApiClient).deleteConnector(certificateBEResultCaptor.getResult().getClientId());
    }

    @Test
    void acceptRegistrationRequestCompletionFails() {

        ResultCaptor<ParticipantDidBE> didBEResultCaptor = new ResultCaptor<>();
        doAnswer(didBEResultCaptor).when(didWebServiceApiClient).generateDidWeb(any());
        ResultCaptor<FhCatalogIdResponse> catalogIdResponseResultCaptor = new ResultCaptor<>();
        doAnswer(catalogIdResponseResultCaptor).when(fhCatalogClient).addParticipantToCatalog(any());
        ResultCaptor<OmejdnConnectorCertificateBE> certificateBEResultCaptor = new ResultCaptor<>();
        doAnswer(certificateBEResultCaptor).when(omejdnConnectorApiClient).addConnector(any());

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.acceptRegistrationRequest(ParticipantRegistrationRequestDAOFake.BAD_COMPLETION_NAME));

        verify(participantRegistrationRequestDao).acceptRegistrationRequest(any());
        verify(didWebServiceApiClient).generateDidWeb(any());
        verify(fhCatalogClient).addParticipantToCatalog(any());
        verify(omejdnConnectorApiClient).addConnector(any());
        verify(didWebServiceApiClient).updateDidWeb(any());
        verify(participantRegistrationRequestDao).completeRegistrationRequest(any(), any(), any(), any());

        verify(didWebServiceApiClient).deleteDidWeb(didBEResultCaptor.getResult().getDid());
        verify(fhCatalogClient).deleteParticipantFromCatalog(catalogIdResponseResultCaptor.getResult().getId());
        verify(omejdnConnectorApiClient).deleteConnector(certificateBEResultCaptor.getResult().getClientId());
    }

    @Test
    void acceptRegistrationRequestNotFound() {

        assertThrows(ParticipantNotFoundException.class,
            () -> sut.acceptRegistrationRequest(ParticipantRegistrationRequestDAOFake.NON_EXISTING_NAME));
    }

    @Test
    void acceptRegistrationRequestBadTransition() {

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.acceptRegistrationRequest(ParticipantRegistrationRequestDAOFake.BAD_TRANSITION_NAME));
    }

    @Test
    void rejectRegistrationRequestSuccess() {

        sut.rejectRegistrationRequest(ParticipantRegistrationRequestDAOFake.EXISTING_NAME);
        verify(participantRegistrationRequestDao).rejectRegistrationRequest(
            ParticipantRegistrationRequestDAOFake.EXISTING_NAME);
    }

    @Test
    void rejectRegistrationRequestNotFound() {

        assertThrows(ParticipantNotFoundException.class,
            () -> sut.rejectRegistrationRequest(ParticipantRegistrationRequestDAOFake.NON_EXISTING_NAME));
    }

    @Test
    void rejectRegistrationRequestBadTransition() {

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.rejectRegistrationRequest(ParticipantRegistrationRequestDAOFake.BAD_TRANSITION_NAME));
    }

    @Test
    void deleteRegistrationRequest() {

        sut.deleteRegistrationRequest(ParticipantRegistrationRequestDAOFake.EXISTING_NAME);
        verify(participantRegistrationRequestDao).deleteRegistrationRequest(
            ParticipantRegistrationRequestDAOFake.EXISTING_NAME);
    }

    @Test
    void deleteRegistrationRequestNotFound() {

        assertThrows(ParticipantNotFoundException.class,
            () -> sut.deleteRegistrationRequest(ParticipantRegistrationRequestDAOFake.NON_EXISTING_NAME));
    }

    @Test
    void deleteRegistrationRequestBadTransition() {

        assertThrows(RegistrationRequestProcessingException.class,
            () -> sut.deleteRegistrationRequest(ParticipantRegistrationRequestDAOFake.BAD_TRANSITION_NAME));
    }

    private PxExtendedLegalParticipantCredentialSubject getParticipantCs() {

        ParticipantRegistrationRequestBE be = ParticipantRegistrationRequestDAOFake.getExampleParticipant();
        GxLegalRegistrationNumberCredentialSubject regNum = be.getLegalRegistrationNumber();

        return PxExtendedLegalParticipantCredentialSubject.builder()
            .id(ParticipantRegistrationRequestDAOFake.NON_EXISTING_NAME).legalRegistrationNumber(
                new GxNestedLegalRegistrationNumberCredentialSubject(regNum.getEori(), regNum.getVatID(),
                    regNum.getLeiCode())).headquarterAddress(be.getHeadquarterAddress())
            .legalAddress(be.getLegalAddress()).name(ParticipantRegistrationRequestDAOFake.NON_EXISTING_NAME)
            .description(be.getDescription()).mailAddress("example@address.com").build();
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
        public FhCatalogClient fhCatalogClient() {

            return Mockito.spy(new FhCatalogClientFake());
        }

        @Bean
        public ParticipantRegistrationRequestDAO participantRegistrationRequestDAO() {

            return Mockito.spy(new ParticipantRegistrationRequestDAOFake());
        }
    }
}
