package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.RequestStatus;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationServiceMapperTest.TestConfig.class,
    ParticipantRegistrationServiceMapper.class })
class ParticipantRegistrationServiceMapperTest {

    private final String participantName = "SomeOrga Inc.";

    private final String participantDescription = "This is an organization.";

    private final String participantAddrCountryCode = "DE";

    private final String participantAddrCountrySubdivisionCode = "DE-BE";

    private final String participantAddrCountryStreetAddress = "Some Street 123";

    private final String participantAddrCountryLocality = "Berlin";

    private final String participantAddrPostalCode = "12345";

    private final String participantRegNumEori = "1234";

    private final String participantRegNumVatID = "5678";

    private final String participantRegNumLeiCode = "9012";

    @Autowired
    private ParticipantRegistrationServiceMapper participantRegistrationServiceMapper;

    @Test
    void possibleParticipantCsToRegistrationRequestListTO() {
        // given
        ParticipantRegistrationRequestBE possibleParticipant = getParticipantRegistrationRequestExample();

        // when
        RegistrationRequestEntryTO to = participantRegistrationServiceMapper.participantRegistrationRequestBEToRegistrationRequestEntryTO(
            possibleParticipant);

        // then
        assertNotNull(to);

        assertEquals(participantName, to.getName());
        assertEquals(participantDescription, to.getDescription());

        assertEquals(participantAddrCountryCode, to.getHeadquarterAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode, to.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, to.getHeadquarterAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, to.getHeadquarterAddress().getLocality());
        assertEquals(participantAddrPostalCode, to.getHeadquarterAddress().getPostalCode());

        assertEquals(participantAddrCountryCode, to.getLegalAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode, to.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, to.getLegalAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, to.getLegalAddress().getLocality());
        assertEquals(participantAddrPostalCode, to.getLegalAddress().getPostalCode());

        assertEquals(participantRegNumEori, to.getLegalRegistrationNumber().getEori());
        assertEquals(participantRegNumVatID, to.getLegalRegistrationNumber().getVatID());
        assertEquals(participantRegNumLeiCode, to.getLegalRegistrationNumber().getLeiCode());
    }

    @Test
    void registrationRequestToCredentialSubject() {

        ParticipantRegistrationRequestBE possibleParticipant = getParticipantRegistrationRequestCompletedExample();
        PxExtendedLegalParticipantCredentialSubject cs = participantRegistrationServiceMapper.participantRegistrationRequestBEToCs(
            possibleParticipant);

        assertEquals(possibleParticipant.getDidData().getDid(), cs.getId());
        assertEquals(possibleParticipant.getName(), cs.getName());
        assertEquals(possibleParticipant.getDescription(), cs.getDescription());

        assertEquals(possibleParticipant.getHeadquarterAddress().getCountryCode(),
            cs.getHeadquarterAddress().getCountryCode());
        assertEquals(possibleParticipant.getHeadquarterAddress().getCountrySubdivisionCode(),
            cs.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(possibleParticipant.getHeadquarterAddress().getStreetAddress(),
            cs.getHeadquarterAddress().getStreetAddress());
        assertEquals(possibleParticipant.getHeadquarterAddress().getLocality(),
            cs.getHeadquarterAddress().getLocality());
        assertEquals(possibleParticipant.getHeadquarterAddress().getPostalCode(),
            cs.getHeadquarterAddress().getPostalCode());

        assertEquals(possibleParticipant.getLegalAddress().getCountryCode(), cs.getLegalAddress().getCountryCode());
        assertEquals(possibleParticipant.getLegalAddress().getCountrySubdivisionCode(),
            cs.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(possibleParticipant.getLegalAddress().getStreetAddress(), cs.getLegalAddress().getStreetAddress());
        assertEquals(possibleParticipant.getLegalAddress().getLocality(), cs.getLegalAddress().getLocality());
        assertEquals(possibleParticipant.getLegalAddress().getPostalCode(), cs.getLegalAddress().getPostalCode());

        assertEquals(possibleParticipant.getLegalRegistrationNumber().getEori(),
            cs.getLegalRegistrationNumber().getEori());
        assertEquals(possibleParticipant.getLegalRegistrationNumber().getVatID(),
            cs.getLegalRegistrationNumber().getVatID());
        assertEquals(possibleParticipant.getLegalRegistrationNumber().getLeiCode(),
            cs.getLegalRegistrationNumber().getLeiCode());
    }

    private GxLegalRegistrationNumberCredentialSubject getGxLegalRegistrationNumberCredentialSubjectExample() {

        GxLegalRegistrationNumberCredentialSubject registrationNumber = new GxLegalRegistrationNumberCredentialSubject();
        registrationNumber.setEori(participantRegNumEori);
        registrationNumber.setVatID(participantRegNumVatID);
        registrationNumber.setLeiCode(participantRegNumLeiCode);
        return registrationNumber;
    }

    private ParticipantRegistrationRequestBE getParticipantRegistrationRequestExample() {

        GxVcard vcard = new GxVcard();
        vcard.setCountryCode(participantAddrCountryCode);
        vcard.setCountrySubdivisionCode(participantAddrCountrySubdivisionCode);
        vcard.setStreetAddress(participantAddrCountryStreetAddress);
        vcard.setLocality(participantAddrCountryLocality);
        vcard.setPostalCode(participantAddrPostalCode);

        return ParticipantRegistrationRequestBE.builder()
            .legalRegistrationNumber(getGxLegalRegistrationNumberCredentialSubjectExample()).legalAddress(vcard)
            .headquarterAddress(vcard).description(participantDescription).name(participantName).build();
    }

    private ParticipantRegistrationRequestBE getParticipantRegistrationRequestCompletedExample() {

        ParticipantRegistrationRequestBE be = getParticipantRegistrationRequestExample();

        ParticipantDidBE didBe = ParticipantDidBE.builder().did("did:web:example.com:participant:123")
            .verificationMethod("did:web:example.com:participant:123#JWK2020").build();

        OmejdnConnectorCertificateBE certificateBe = OmejdnConnectorCertificateBE.builder().keystore("123")
            .password("456").clientId("11:22:33").clientName("did:web:example.com:participant:123").scope("some-scope")
            .build();

        be.setDidData(didBe);
        be.setOmejdnConnectorCertificate(certificateBe);
        be.setStatus(RequestStatus.COMPLETED);
        be.setVpLink("http://example.com/did:web:example.com:participant:123");

        return be;
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ParticipantRegistrationServiceMapper participantRegistrationServiceMapper() {

            return Mappers.getMapper(ParticipantRegistrationServiceMapper.class);
        }

    }
}