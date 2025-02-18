/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.px.participants.PxParticipantExtensionCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationRestApiMapperTest.TestConfig.class })
class ParticipantRegistrationRestApiMapperTest {

    @Autowired
    private ParticipantRegistrationRestApiMapper participantRegistrationRestApiMapper;

    @Test
    void mapCredentialSubjectsToExtendedLegalParticipantCs() {

        // given
        GxLegalParticipantCredentialSubject participant = getGxLegalParticipantCredentialSubjectExample();
        GxLegalRegistrationNumberCredentialSubject registrationNumber = getGxLegalRegistrationNumberCredentialSubjectExample();
        PxParticipantExtensionCredentialSubject extensionCs = getPxParticipantExtensionCredentialSubjectExample();

        CreateRegistrationRequestTO request = CreateRegistrationRequestTO.builder().participantCs(participant)
            .registrationNumberCs(registrationNumber).participantExtensionCs(extensionCs).build();

        // when
        PxExtendedLegalParticipantCredentialSubject participantCs = participantRegistrationRestApiMapper.credentialSubjectsToExtendedLegalParticipantCs(
            request);

        // then
        assertEquals(participant.getName(), participantCs.getName());
        assertEquals(participant.getDescription(), participantCs.getDescription());

        assertEquals(participant.getHeadquarterAddress().getCountryCode(),
            participantCs.getHeadquarterAddress().getCountryCode());
        assertEquals(participant.getHeadquarterAddress().getCountrySubdivisionCode(),
            participantCs.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(participant.getHeadquarterAddress().getStreetAddress(),
            participantCs.getHeadquarterAddress().getStreetAddress());
        assertEquals(participant.getHeadquarterAddress().getLocality(),
            participantCs.getHeadquarterAddress().getLocality());
        assertEquals(participant.getHeadquarterAddress().getPostalCode(),
            participantCs.getHeadquarterAddress().getPostalCode());

        assertEquals(participant.getLegalAddress().getCountryCode(), participantCs.getLegalAddress().getCountryCode());
        assertEquals(participant.getLegalAddress().getCountrySubdivisionCode(),
            participantCs.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(participant.getLegalAddress().getStreetAddress(),
            participantCs.getLegalAddress().getStreetAddress());
        assertEquals(participant.getLegalAddress().getLocality(), participantCs.getLegalAddress().getLocality());
        assertEquals(participant.getLegalAddress().getPostalCode(), participantCs.getLegalAddress().getPostalCode());

        assertEquals(registrationNumber.getEori(), participantCs.getLegalRegistrationNumber().getEori());
        assertEquals(registrationNumber.getVatID(), participantCs.getLegalRegistrationNumber().getVatID());
        assertEquals(registrationNumber.getLeiCode(), participantCs.getLegalRegistrationNumber().getLeiCode());

        assertEquals(extensionCs.getMailAddress(), participantCs.getMailAddress());

    }

    private GxLegalRegistrationNumberCredentialSubject getGxLegalRegistrationNumberCredentialSubjectExample() {

        GxLegalRegistrationNumberCredentialSubject registrationNumber = new GxLegalRegistrationNumberCredentialSubject();
        registrationNumber.setEori("1234");
        registrationNumber.setVatID("5678");
        registrationNumber.setLeiCode("9012");
        return registrationNumber;
    }

    private PxParticipantExtensionCredentialSubject getPxParticipantExtensionCredentialSubjectExample() {

        PxParticipantExtensionCredentialSubject cs = new PxParticipantExtensionCredentialSubject();
        cs.setMailAddress("example@example.com");
        return cs;
    }

    private GxLegalParticipantCredentialSubject getGxLegalParticipantCredentialSubjectExample() {

        GxVcard vcard = new GxVcard();
        vcard.setCountryCode("DE");
        vcard.setCountrySubdivisionCode("DE-BE");
        vcard.setStreetAddress("Some Street 123");
        vcard.setLocality("Berlin");
        vcard.setPostalCode("12345");

        GxLegalParticipantCredentialSubject participant = new GxLegalParticipantCredentialSubject();
        participant.setId("1234");
        participant.setName("SomeOrga Inc.");
        participant.setDescription("This is an organization.");
        participant.setHeadquarterAddress(vcard);
        participant.setLegalAddress(vcard);
        return participant;
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public ParticipantRegistrationRestApiMapper participantCredentialMapper() {

            return Mappers.getMapper(ParticipantRegistrationRestApiMapper.class);
        }

    }
}
