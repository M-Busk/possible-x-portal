package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

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
        participantRegistrationRequestDAO.acceptRegistrationRequest("validName");
        verify(participantRegistrationRequestRepository, times(2)).save(any());
    }

    @Test
    void completeRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        ParticipantMetadataBE metadata = getParticipantMetadata();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant, metadata);
        participantRegistrationRequestDAO.acceptRegistrationRequest("validName");
        participantRegistrationRequestDAO.completeRegistrationRequest("validName");
        verify(participantRegistrationRequestRepository, times(3)).save(any());
    }

    @Test
    void rejectAndDeleteRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        ParticipantMetadataBE metadata = getParticipantMetadata();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant, metadata);
        participantRegistrationRequestDAO.rejectRegistrationRequest("validName");
        participantRegistrationRequestDAO.deleteRegistrationRequest("validName");
        verify(participantRegistrationRequestRepository, times(2)).save(any());

        verify(participantRegistrationRequestRepository).delete(any());
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
            .headquarterAddress(vcard).name("validName").description("validDescription").build();
    }
}
