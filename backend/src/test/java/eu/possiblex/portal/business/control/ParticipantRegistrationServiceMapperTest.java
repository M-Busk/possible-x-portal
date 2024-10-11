package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
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
        RegistrationRequestEntryTO to = participantRegistrationServiceMapper.participantRegistrationRequestBEToRegistrationRequestEntryTO(possibleParticipant);

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
            .legalRegistrationNumber(getGxLegalRegistrationNumberCredentialSubjectExample())
            .legalAddress(vcard)
            .headquarterAddress(vcard)
            .description(participantDescription)
            .name(participantName).build();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ParticipantRegistrationServiceMapper participantRegistrationServiceMapper() {

            return Mappers.getMapper(ParticipantRegistrationServiceMapper.class);
        }

    }
}