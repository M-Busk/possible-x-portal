package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationRestApiMapperTest.TestConfig.class, ParticipantRegistrationRestApiMapper.class })
class ParticipantRegistrationRestApiMapperTest {

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
    private ParticipantRegistrationRestApiMapper participantRegistrationRestApiMapper;

    @Test
    void mapCredentialSubjectsToExtendedLegalParticipantCs() {

        // given
        GxLegalParticipantCredentialSubject participant = getGxLegalParticipantCredentialSubjectExample();
        GxLegalRegistrationNumberCredentialSubject registrationNumber = getGxLegalRegistrationNumberCredentialSubjectExample();

        // when
        PxExtendedLegalParticipantCredentialSubject participantCs = participantRegistrationRestApiMapper.credentialSubjectsToExtendedLegalParticipantCs(
            participant, registrationNumber);

        // then
        assertEquals(participantName, participantCs.getName());
        assertEquals(participantDescription, participantCs.getDescription());

        assertEquals(participantAddrCountryCode, participantCs.getHeadquarterAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode,
            participantCs.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, participantCs.getHeadquarterAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, participantCs.getHeadquarterAddress().getLocality());
        assertEquals(participantAddrPostalCode, participantCs.getHeadquarterAddress().getPostalCode());

        assertEquals(participantAddrCountryCode, participantCs.getLegalAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode,
            participantCs.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, participantCs.getLegalAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, participantCs.getLegalAddress().getLocality());
        assertEquals(participantAddrPostalCode, participantCs.getLegalAddress().getPostalCode());

        assertEquals(participantRegNumEori, participantCs.getLegalRegistrationNumber().getEori());
        assertEquals(participantRegNumVatID, participantCs.getLegalRegistrationNumber().getVatID());
        assertEquals(participantRegNumLeiCode, participantCs.getLegalRegistrationNumber().getLeiCode());

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
        public ParticipantRegistrationRestApiMapper participantCredentialMapper() {

            return Mappers.getMapper(ParticipantRegistrationRestApiMapper.class);
        }

    }
}
