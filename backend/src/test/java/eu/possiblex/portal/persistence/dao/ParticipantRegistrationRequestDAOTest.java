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

package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.PortalApplication;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.RequestStatus;
import eu.possiblex.portal.business.entity.credentials.px.GxNestedLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityNotFoundException;
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityStateTransitionException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestPropertySource(properties = { "version.no = thisistheversion", "version.date = 21.03.2022" })
@ContextConfiguration(classes = { ParticipantRegistrationRequestDAOImpl.class, PortalApplication.class })
@Transactional
class ParticipantRegistrationRequestDAOTest {
    private static final String EXAMPLE_PARTICIPANT_NAME = "exampleParticipantName";

    @SpyBean
    private ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    @Autowired
    private ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    @BeforeEach
    void setUp() {

        participantRegistrationRequestRepository.deleteAll();

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant);

        reset(participantRegistrationRequestRepository);
    }

    @Test
    void saveParticipantRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant);
        verify(participantRegistrationRequestRepository).save(any());
    }

    @Test
    void acceptRegistrationRequestSuccess() {

        ParticipantRegistrationRequestBE repoParticipant = participantRegistrationRequestDAO.getRegistrationRequestByName(
            EXAMPLE_PARTICIPANT_NAME);
        assertEquals(RequestStatus.NEW, repoParticipant.getStatus());

        participantRegistrationRequestDAO.acceptRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);
        repoParticipant = participantRegistrationRequestDAO.getRegistrationRequestByName(EXAMPLE_PARTICIPANT_NAME);
        assertEquals(RequestStatus.ACCEPTED, repoParticipant.getStatus());
    }

    @Test
    void acceptRegistrationRequestNotExistingParticipant() {

        assertThrows(ParticipantEntityNotFoundException.class,
            () -> participantRegistrationRequestDAO.acceptRegistrationRequest("notExistingParticipant"));
    }

    @Test
    void acceptRegistrationRequestCompletedParticipant() {

        participantRegistrationRequestDAO.acceptRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);
        participantRegistrationRequestDAO.completeRegistrationRequest(EXAMPLE_PARTICIPANT_NAME,
            new ParticipantDidBE("validDid", "validVerificationMethod"), "validVpLink",
            new OmejdnConnectorCertificateBE("validClientId", "validPassword", "validKeystore", "123", "1234"));

        assertThrows(ParticipantEntityStateTransitionException.class,
            () -> participantRegistrationRequestDAO.acceptRegistrationRequest(EXAMPLE_PARTICIPANT_NAME));
    }

    @Test
    void acceptRegistrationRequestRejectedParticipant() {

        participantRegistrationRequestDAO.rejectRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);

        assertThrows(ParticipantEntityStateTransitionException.class,
            () -> participantRegistrationRequestDAO.acceptRegistrationRequest(EXAMPLE_PARTICIPANT_NAME));
    }

    @Test
    void completeRegistrationRequestSuccess() {

        participantRegistrationRequestDAO.acceptRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);
        ParticipantRegistrationRequestBE repoParticipant = participantRegistrationRequestDAO.getRegistrationRequestByName(
            EXAMPLE_PARTICIPANT_NAME);
        assertEquals(RequestStatus.ACCEPTED, repoParticipant.getStatus());

        participantRegistrationRequestDAO.completeRegistrationRequest(EXAMPLE_PARTICIPANT_NAME,
            new ParticipantDidBE("validDid", "validVerificationMethod"), "validVpLink",
            new OmejdnConnectorCertificateBE("validClientId", "validPassword", "validKeystore", "123", "1234"));
        repoParticipant = participantRegistrationRequestDAO.getRegistrationRequestByName(EXAMPLE_PARTICIPANT_NAME);
        assertEquals(RequestStatus.COMPLETED, repoParticipant.getStatus());
    }

    @Test
    void completeRegistrationRequestNotExistingParticipant() {

        ParticipantDidBE participantDidBE = new ParticipantDidBE("validDid", "validVerificationMethod");
        OmejdnConnectorCertificateBE omejdnConnectorCertificateBE = new OmejdnConnectorCertificateBE("validClientId",
            "validPassword", "validKeystore", "123", "1234");

        assertThrows(ParticipantEntityNotFoundException.class,
            () -> participantRegistrationRequestDAO.completeRegistrationRequest("notExistingParticipant",
                participantDidBE, "validVpLink", omejdnConnectorCertificateBE));
    }

    @Test
    void completeRegistrationRequestNotAcceptedParticipant() {

        ParticipantDidBE participantDidBE = new ParticipantDidBE("validDid", "validVerificationMethod");
        OmejdnConnectorCertificateBE omejdnConnectorCertificateBE = new OmejdnConnectorCertificateBE("validClientId",
            "validPassword", "validKeystore", "123", "1234");

        assertThrows(ParticipantEntityStateTransitionException.class,
            () -> participantRegistrationRequestDAO.completeRegistrationRequest(EXAMPLE_PARTICIPANT_NAME,
                participantDidBE, "validVpLink", omejdnConnectorCertificateBE));
    }

    @Test
    void rejectRegistrationRequestSuccess() {

        participantRegistrationRequestDAO.rejectRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);
        ParticipantRegistrationRequestBE repoParticipant = participantRegistrationRequestDAO.getRegistrationRequestByName(
            EXAMPLE_PARTICIPANT_NAME);
        assertEquals(RequestStatus.REJECTED, repoParticipant.getStatus());

    }

    @Test
    void rejectRegistrationRequestNotExistingParticipant() {

        assertThrows(ParticipantEntityNotFoundException.class,
            () -> participantRegistrationRequestDAO.rejectRegistrationRequest("notExistingParticipant"));
    }

    @Test
    void rejectRegistrationRequestCompletedParticipant() {

        participantRegistrationRequestDAO.acceptRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);
        participantRegistrationRequestDAO.completeRegistrationRequest(EXAMPLE_PARTICIPANT_NAME,
            new ParticipantDidBE("validDid", "validVerificationMethod"), "validVpLink",
            new OmejdnConnectorCertificateBE("validClientId", "validPassword", "validKeystore", "123", "1234"));

        assertThrows(ParticipantEntityStateTransitionException.class,
            () -> participantRegistrationRequestDAO.rejectRegistrationRequest(EXAMPLE_PARTICIPANT_NAME));
    }

    @Test
    void deleteRegistrationRequestSuccess() {

        participantRegistrationRequestDAO.deleteRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);
        ParticipantRegistrationRequestBE repoParticipant = participantRegistrationRequestDAO.getRegistrationRequestByName(
            EXAMPLE_PARTICIPANT_NAME);
        assertNull(repoParticipant);
    }

    @Test
    void deleteRegistrationRequestNotExistingParticipant() {

        assertThrows(ParticipantEntityNotFoundException.class,
            () -> participantRegistrationRequestDAO.deleteRegistrationRequest("notExistingParticipant"));
    }

    @Test
    void deleteRegistrationRequestCompletedParticipant() {

        participantRegistrationRequestDAO.acceptRegistrationRequest(EXAMPLE_PARTICIPANT_NAME);
        participantRegistrationRequestDAO.completeRegistrationRequest(EXAMPLE_PARTICIPANT_NAME,
            new ParticipantDidBE("validDid", "validVerificationMethod"), "validVpLink",
            new OmejdnConnectorCertificateBE("validClientId", "validPassword", "validKeystore", "123", "1234"));

        assertThrows(ParticipantEntityStateTransitionException.class,
            () -> participantRegistrationRequestDAO.deleteRegistrationRequest(EXAMPLE_PARTICIPANT_NAME));
    }

    @Test
    void getAllParticipantRegistrationRequests() {

        participantRegistrationRequestDAO.getRegistrationRequests(PageRequest.of(0, 1));
        verify(participantRegistrationRequestRepository).findAll(any(Pageable.class));
    }

    @Test
    void getParticipantRegistrationByDidSuccess() {

        participantRegistrationRequestDAO.getRegistrationRequestByDid("did:web:1234");
        verify(participantRegistrationRequestRepository).findByDidData_Did("did:web:1234");
    }

    @Test
    void getParticipantRegistrationByDidNotExistingParticipant() {

        assertNull(participantRegistrationRequestDAO.getRegistrationRequestByDid("notExistingParticipant"));
    }

    @Test
    void getParticipantRegistrationByNameSuccess() {

        participantRegistrationRequestDAO.getRegistrationRequestByName("name");
        verify(participantRegistrationRequestRepository).findByName("name");
    }

    @Test
    void getParticipantRegistrationByNameNotExistingParticipant() {

        assertNull(participantRegistrationRequestDAO.getRegistrationRequestByName("notExistingParticipant"));
    }

    private PxExtendedLegalParticipantCredentialSubject getParticipant() {

        GxVcard vcard = new GxVcard();
        vcard.setCountryCode("validCountryCode");
        vcard.setCountrySubdivisionCode("validSubdivisionCode");
        vcard.setStreetAddress("validStreetAddress");
        vcard.setLocality("validLocality");
        vcard.setPostalCode("validPostalCode");

        return PxExtendedLegalParticipantCredentialSubject.builder().id("validId").legalRegistrationNumber(
                new GxNestedLegalRegistrationNumberCredentialSubject("validEori", "validVatId", "validLeiCode"))
            .headquarterAddress(vcard).legalAddress(vcard).name(EXAMPLE_PARTICIPANT_NAME)
            .description("validDescription").mailAddress("example@address.com").build();
    }
}
