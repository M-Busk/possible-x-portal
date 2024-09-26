package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantCredentialMapperTest.TestConfig.class, ParticipantCredentialMapper.class })
class ParticipantCredentialMapperTest {

    private final String participantId = "1234";

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
    private ParticipantCredentialMapper participantCredentialMapper;

    @Test
    void mapCredentialSubjectsToBE() {

        // given
        GxLegalParticipantCredentialSubject participant = getGxLegalParticipantCredentialSubjectExample();
        GxLegalRegistrationNumberCredentialSubject registrationNumber = getGxLegalRegistrationNumberCredentialSubjectExample();

        // when
        PossibleParticipantBE participantBE = participantCredentialMapper.credentialSubjectsToBE(participant,
            registrationNumber);

        // then
        assertEquals(participantName, participantBE.getName());
        assertEquals(participantDescription, participantBE.getDescription());

        assertEquals(participantAddrCountryCode, participantBE.getHeadquarterAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode,
            participantBE.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, participantBE.getHeadquarterAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, participantBE.getHeadquarterAddress().getLocality());
        assertEquals(participantAddrPostalCode, participantBE.getHeadquarterAddress().getPostalCode());

        assertEquals(participantAddrCountryCode, participantBE.getLegalAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode,
            participantBE.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, participantBE.getLegalAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, participantBE.getLegalAddress().getLocality());
        assertEquals(participantAddrPostalCode, participantBE.getLegalAddress().getPostalCode());

        assertEquals(participantRegNumEori, participantBE.getLegalRegistrationNumber().getEori());
        assertEquals(participantRegNumVatID, participantBE.getLegalRegistrationNumber().getVatID());
        assertEquals(participantRegNumLeiCode, participantBE.getLegalRegistrationNumber().getLeiCode());

    }

    private GxLegalRegistrationNumberCredentialSubject getGxLegalRegistrationNumberCredentialSubjectExample() {

        GxLegalRegistrationNumberCredentialSubject registrationNumber = new GxLegalRegistrationNumberCredentialSubject();
        registrationNumber.setEori(participantRegNumEori);
        registrationNumber.setVatID(participantRegNumVatID);
        registrationNumber.setLeiCode(participantRegNumLeiCode);
        return registrationNumber;
    }

    private GxLegalParticipantCredentialSubject getGxLegalParticipantCredentialSubjectExample() {

        GxVcard vcard = new GxVcard();
        vcard.setCountryCode(participantAddrCountryCode);
        vcard.setCountrySubdivisionCode(participantAddrCountrySubdivisionCode);
        vcard.setStreetAddress(participantAddrCountryStreetAddress);
        vcard.setLocality(participantAddrCountryLocality);
        vcard.setPostalCode(participantAddrPostalCode);

        GxLegalParticipantCredentialSubject participant = new GxLegalParticipantCredentialSubject();
        participant.setId(participantId);
        participant.setName(participantName);
        participant.setDescription(participantDescription);
        participant.setHeadquarterAddress(vcard);
        participant.setLegalAddress(vcard);
        return participant;
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ParticipantCredentialMapper participantCredentialMapper() {

            return Mappers.getMapper(ParticipantCredentialMapper.class);
        }

    }
}
