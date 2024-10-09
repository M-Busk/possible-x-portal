package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.control.DidWebServiceApiClientFake;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.RequestStatus;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.persistence.entity.DidDataEntity;
import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class ParticipantRegistrationRequestDAOTest {
    @SpyBean
    private ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    @Autowired
    private ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    @Test
    void saveParticipantRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        ParticipantMetadataBE metadata = getParticipantMetadata();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant, metadata);
        verify(participantRegistrationRequestRepository).save(any());
    }

    @Test
    void getAllParticipantRegistrationRequests() {

        participantRegistrationRequestDAO.getAllParticipantRegistrationRequests();
        verify(participantRegistrationRequestRepository).findAll();
    }

    @Test
    void acceptRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        ParticipantMetadataBE metadata = getParticipantMetadata();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant, metadata);
        participantRegistrationRequestDAO.acceptRegistrationRequest(participant.getName());
        verify(participantRegistrationRequestRepository, times(1)).save(any());

        List<ParticipantRegistrationRequestBE> repoParticipants = participantRegistrationRequestDAO.getAllParticipantRegistrationRequests();
        assertEquals(1, repoParticipants.size());
        ParticipantRegistrationRequestBE repoParticipant = repoParticipants.get(0);
        assertEquals(participant.getName(), repoParticipant.getName());
        assertEquals(participant.getDescription(), repoParticipant.getDescription());

        assertEquals(participant.getHeadquarterAddress().getCountryCode(),
            repoParticipant.getHeadquarterAddress().getCountryCode());
        assertEquals(participant.getHeadquarterAddress().getCountrySubdivisionCode(),
            repoParticipant.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(participant.getHeadquarterAddress().getStreetAddress(),
            repoParticipant.getHeadquarterAddress().getStreetAddress());
        assertEquals(participant.getHeadquarterAddress().getLocality(),
            repoParticipant.getHeadquarterAddress().getLocality());
        assertEquals(participant.getHeadquarterAddress().getPostalCode(),
            repoParticipant.getHeadquarterAddress().getPostalCode());

        assertEquals(participant.getLegalAddress().getCountryCode(),
            repoParticipant.getLegalAddress().getCountryCode());
        assertEquals(participant.getLegalAddress().getCountrySubdivisionCode(),
            repoParticipant.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(participant.getLegalAddress().getStreetAddress(),
            repoParticipant.getLegalAddress().getStreetAddress());
        assertEquals(participant.getLegalAddress().getLocality(), repoParticipant.getLegalAddress().getLocality());
        assertEquals(participant.getLegalAddress().getPostalCode(), repoParticipant.getLegalAddress().getPostalCode());

        assertEquals(participant.getLegalRegistrationNumber().getEori(),
            repoParticipant.getLegalRegistrationNumber().getEori());
        assertEquals(participant.getLegalRegistrationNumber().getVatID(),
            repoParticipant.getLegalRegistrationNumber().getVatID());
        assertEquals(participant.getLegalRegistrationNumber().getLeiCode(),
            repoParticipant.getLegalRegistrationNumber().getLeiCode());

        assertEquals(RequestStatus.ACCEPTED, repoParticipant.getStatus());
    }

    @Test
    void saveDidData() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        ParticipantMetadataBE metadata = getParticipantMetadata();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant, metadata);
        participantRegistrationRequestDAO.acceptRegistrationRequest(participant.getName());
        participantRegistrationRequestDAO.storeRegistrationRequestDid(participant.getName(),
            new ParticipantDidBE(DidWebServiceApiClientFake.EXAMPLE_DID,
                DidWebServiceApiClientFake.EXAMPLE_VERIFICATION_METHOD));
        verify(participantRegistrationRequestRepository, times(1)).save(any());

        participantRegistrationRequestDAO.completeRegistrationRequest(participant.getName());

        List<ParticipantRegistrationRequestBE> repoParticipants = participantRegistrationRequestDAO.getAllParticipantRegistrationRequests();
        assertEquals(1, repoParticipants.size());
        ParticipantRegistrationRequestBE repoParticipant = repoParticipants.get(0);

        assertEquals(DidWebServiceApiClientFake.EXAMPLE_DID, repoParticipant.getDidData().getDid());
        assertEquals(DidWebServiceApiClientFake.EXAMPLE_VERIFICATION_METHOD,
            repoParticipant.getDidData().getVerificationMethod());

        assertEquals(RequestStatus.COMPLETED, repoParticipant.getStatus());
    }

    @Test
    void completeRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        ParticipantMetadataBE metadata = getParticipantMetadata();


        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant, metadata);
        participantRegistrationRequestDAO.acceptRegistrationRequest(participant.getName());
        participantRegistrationRequestDAO.storeRegistrationRequestDaps(participant.getName(), new OmejdnConnectorCertificateBE( "validClientId", "validPassword", "validKeystore", "123","1234"));
        participantRegistrationRequestDAO.storeRegistrationRequestVpLink(participant.getName(), "validVpLink");
        participantRegistrationRequestDAO.storeRegistrationRequestDid(participant.getName(), new ParticipantDidBE("validDid", "validVerificationMethod"));
        participantRegistrationRequestDAO.completeRegistrationRequest(participant.getName());
        verify(participantRegistrationRequestRepository, times(2)).save(any());
        assertNotNull(participantRegistrationRequestRepository.findByName(participant.getName()).getOmejdnConnectorCertificate());
    }

    @Test
    void rejectAndDeleteRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        ParticipantMetadataBE metadata = getParticipantMetadata();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant, metadata);
        participantRegistrationRequestDAO.rejectRegistrationRequest("validName");
        participantRegistrationRequestDAO.deleteRegistrationRequest("validName");
        verify(participantRegistrationRequestRepository, times(1)).save(any());

        verify(participantRegistrationRequestRepository).delete(any());
        assertTrue(participantRegistrationRequestRepository.findAll().isEmpty());
    }

    private ParticipantMetadataBE getParticipantMetadata() {

        return ParticipantMetadataBE.builder().emailAddress("example@address.com").build();
    }

    private PxExtendedLegalParticipantCredentialSubject getParticipant() {

        GxVcard vcard = new GxVcard();
        vcard.setCountryCode("validCountryCode");
        vcard.setCountrySubdivisionCode("validSubdivisionCode");
        vcard.setStreetAddress("validStreetAddress");
        vcard.setLocality("validLocality");
        vcard.setPostalCode("validPostalCode");

        return PxExtendedLegalParticipantCredentialSubject.builder().id("validId").legalRegistrationNumber(
                new GxLegalRegistrationNumberCredentialSubject("validEori", "validVatId", "validLeiCode"))
            .headquarterAddress(vcard).legalAddress(vcard).name("validName").description("validDescription").build();
    }
}
