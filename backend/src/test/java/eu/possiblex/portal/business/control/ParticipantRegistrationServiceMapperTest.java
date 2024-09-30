package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationServiceMapperTest.TestConfig.class,
    ParticipantRegistrationServiceMapper.class })
class ParticipantRegistrationServiceMapperTest {
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
    private ParticipantRegistrationServiceMapper participantRegistrationServiceMapper;

    @Test
    void possibleParticipantCsToRegistrationRequestListTO() {
        // given
        GxLegalParticipantCredentialSubject gxLegalParticipantCredentialSubject = getGxLegalParticipantCredentialSubjectExample();

        // when
        PxExtendedLegalParticipantCredentialSubject possibleParticipantCs = PxExtendedLegalParticipantCredentialSubject.builder()
            .legalRegistrationNumber(getGxLegalRegistrationNumberCredentialSubjectExample())
            .legalAddress(gxLegalParticipantCredentialSubject.getLegalAddress())
            .headquarterAddress(gxLegalParticipantCredentialSubject.getHeadquarterAddress())
            .id(gxLegalParticipantCredentialSubject.getId())
            .description(gxLegalParticipantCredentialSubject.getDescription())
            .name(gxLegalParticipantCredentialSubject.getName()).build();

        // then
        RegistrationRequestListTO listTO = participantRegistrationServiceMapper.pxExtendedLegalParticipantCsToRegistrationRequestListTO(
            possibleParticipantCs);

        assertNotNull(listTO);

        assertEquals(participantName, listTO.getName());
        assertEquals(participantDescription, listTO.getDescription());

        assertEquals(participantAddrCountryCode, listTO.getHeadquarterAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode, listTO.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, listTO.getHeadquarterAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, listTO.getHeadquarterAddress().getLocality());
        assertEquals(participantAddrPostalCode, listTO.getHeadquarterAddress().getPostalCode());

        assertEquals(participantAddrCountryCode, listTO.getLegalAddress().getCountryCode());
        assertEquals(participantAddrCountrySubdivisionCode, listTO.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(participantAddrCountryStreetAddress, listTO.getLegalAddress().getStreetAddress());
        assertEquals(participantAddrCountryLocality, listTO.getLegalAddress().getLocality());
        assertEquals(participantAddrPostalCode, listTO.getLegalAddress().getPostalCode());

        assertEquals(participantRegNumEori, listTO.getLegalRegistrationNumber().getEori());
        assertEquals(participantRegNumVatID, listTO.getLegalRegistrationNumber().getVatID());
        assertEquals(participantRegNumLeiCode, listTO.getLegalRegistrationNumber().getLeiCode());
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
        public ParticipantRegistrationServiceMapper participantRegistrationServiceMapper() {

            return Mappers.getMapper(ParticipantRegistrationServiceMapper.class);
        }

    }
}