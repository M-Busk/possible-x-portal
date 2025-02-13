package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.RequestStatus;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityNotFoundException;
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityStateTransitionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ParticipantRegistrationRequestDAOFake implements ParticipantRegistrationRequestDAO {

    public static final String EXISTING_NAME = "existingName";

    public static final String EXISTING_DID = "did:web:existingDid.me";

    public static final String NON_EXISTING_DID = "nonExistingDid";

    public static final String NON_EXISTING_NAME = "nonExistingName";

    public static final String BAD_TRANSITION_NAME = "badTransitionName";

    public static final String BAD_COMPLETION_NAME = "badCompletionName";

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
    public Page<ParticipantRegistrationRequestBE> getRegistrationRequests(Pageable pageable) {

        ParticipantRegistrationRequestBE participant1 = getExampleParticipant();
        ParticipantRegistrationRequestBE participant2 = getExampleParticipant();
        participant2.setName("anotherName");

        List<ParticipantRegistrationRequestBE> beList = List.of(participant1, participant2);
        List<ParticipantRegistrationRequestBE> responseList = new ArrayList<>();

        for (int i = 0; i < Math.min(pageable.getPageSize(), beList.size()); i++) {
            responseList.add(beList.get(i));
        }

        return new PageImpl<>(responseList);
    }

    @Override
    public ParticipantRegistrationRequestBE getRegistrationRequestByDid(String did) {

        if (did.equals(NON_EXISTING_DID)) {
            return null;
        }

        ParticipantRegistrationRequestBE be = getExampleParticipant();
        be.getDidData().setDid(did);

        return be;
    }

    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant) {
        // request worked
    }

    @Override
    public void acceptRegistrationRequest(String id) {

        handleIdExceptions(id);

        // request worked
    }

    @Override
    public void rejectRegistrationRequest(String id) {

        handleIdExceptions(id);

        // request worked
    }

    @Override
    public void deleteRegistrationRequest(String id) {

        handleIdExceptions(id);

        // request worked
    }

    @Override
    public void completeRegistrationRequest(String id, ParticipantDidBE did, String vpLink,
        OmejdnConnectorCertificateBE certificate) {

        handleIdExceptions(id);

        if (id.equals(BAD_COMPLETION_NAME)) {
            throw new ParticipantEntityStateTransitionException("Bad Transition");
        }

        // request worked
    }

    @Override
    public ParticipantRegistrationRequestBE getRegistrationRequestByName(String name) {

        if (name.equals(NON_EXISTING_NAME)) {
            return null;
        }

        return getExampleParticipant();

    }

    private void handleIdExceptions(String id) {

        if (id.equals(NON_EXISTING_NAME)) {
            throw new ParticipantEntityNotFoundException("Participant not found");
        }

        if (id.equals(BAD_TRANSITION_NAME)) {
            throw new ParticipantEntityStateTransitionException("Bad Transition");
        }
    }
}
