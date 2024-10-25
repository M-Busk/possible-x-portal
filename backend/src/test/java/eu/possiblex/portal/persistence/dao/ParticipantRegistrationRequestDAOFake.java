package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.RequestStatus;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
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

        ParticipantDidBE didData = new ParticipantDidBE();
        didData.setDid("validDid");
        didData.setVerificationMethod("validDid#method");

        OmejdnConnectorCertificateBE certificate = new OmejdnConnectorCertificateBE();
        certificate.setClientId("12:34:56");
        certificate.setClientName("some client");
        certificate.setScope("SOME_SCOPE");
        certificate.setKeystore("1234");
        certificate.setPassword("1234");

        return ParticipantRegistrationRequestBE.builder().legalRegistrationNumber(
                new GxLegalRegistrationNumberCredentialSubject("validEori", "validVatId", "validLeiCode"))
            .headquarterAddress(vcard).legalAddress(vcard).name("validName").description("validDescription")
            .didData(didData).omejdnConnectorCertificate(certificate).status(RequestStatus.COMPLETED).build();
    }

    @Override
    public List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests() {

        return List.of(getExampleParticipant());
    }

    @Override
    public ParticipantRegistrationRequestBE getRegistrationRequestByDid(String did) {

        return getExampleParticipant();
    }

    @Override
    public void storeRegistrationRequestVpLink(String id, String vpLink) {

    }

    @Override
    public void storeRegistrationRequestDaps(String id, OmejdnConnectorCertificateBE certificate) {

    }

    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant) {
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

    }

    @Override
    public void storeRegistrationRequestDid(String id, ParticipantDidBE to) {
        // request worked
    }
}
