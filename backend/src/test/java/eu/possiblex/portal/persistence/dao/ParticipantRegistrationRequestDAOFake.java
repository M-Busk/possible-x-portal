package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;

import java.util.List;

public class ParticipantRegistrationRequestDAOFake implements ParticipantRegistrationRequestDAO {

    public static ParticipantRegistrationRequestBE getExampleParticipant() {

        GxVcard vcard = new GxVcard();
        vcard.setCountryCode("validCountryCode");
        vcard.setCountrySubdivisionCode("validSubdivisionCode");
        vcard.setStreetAddress("validStreetAddress");
        vcard.setLocality("validLocality");
        vcard.setPostalCode("validPostalCode");

        return ParticipantRegistrationRequestBE.builder().legalRegistrationNumber(
                new GxLegalRegistrationNumberCredentialSubject("validEori", "validVatId", "validLeiCode"))
            .headquarterAddress(vcard).legalAddress(vcard).name("validName").description("validDescription").build();
    }

    @Override
    public List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests() {

        return List.of(getExampleParticipant());
    }

    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant, ParticipantMetadataBE metadata) {
        // request worked
    }

    @Override
    public void acceptRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void rejectRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void deleteRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void completeRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void storeRegistrationRequestDid(String id, ParticipantDidBE to) {
        // request worked
    }
}
