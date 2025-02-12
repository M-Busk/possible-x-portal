package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.GxNestedLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.entity.*;
import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationEntityMapperTest.TestConfig.class,
    ParticipantRegistrationEntityMapper.class })
class ParticipantRegistrationEntityMapperTest {

    @Autowired
    private ParticipantRegistrationEntityMapper participantRegistrationServiceMapper;

    @Test
    void mapParticipantCredentialSubjectToEntity() {
        // given
        PxExtendedLegalParticipantCredentialSubject cs = getExampleParticipantCs();
        // when
        ParticipantRegistrationRequestEntity entity = participantRegistrationServiceMapper.pxExtendedLegalParticipantCsToNewEntity(
            cs);
        // then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
        assertNull(entity.getOmejdnConnectorCertificate());
        assertNull(entity.getDidData());
        assertNull(entity.getVpLink());
        assertNull(entity.getLegalRegistrationNumber().getId());
        assertNull(entity.getHeadquarterAddress().getId());
        assertNull(entity.getLegalAddress().getId());

        assertEquals(RequestStatus.NEW, entity.getStatus());
        assertEquals(cs.getName(), entity.getName());
        assertEquals(cs.getDescription(), entity.getDescription());
        assertEquals(cs.getMailAddress(), entity.getEmailAddress());

        assertEquals(cs.getLegalAddress().getCountryCode(), entity.getLegalAddress().getCountryCode());
        assertEquals(cs.getLegalAddress().getCountrySubdivisionCode(),
            entity.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(cs.getLegalAddress().getStreetAddress(), entity.getLegalAddress().getStreetAddress());
        assertEquals(cs.getLegalAddress().getLocality(), entity.getLegalAddress().getLocality());
        assertEquals(cs.getLegalAddress().getPostalCode(), entity.getLegalAddress().getPostalCode());

        assertEquals(cs.getHeadquarterAddress().getCountryCode(), entity.getHeadquarterAddress().getCountryCode());
        assertEquals(cs.getHeadquarterAddress().getCountrySubdivisionCode(),
            entity.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(cs.getHeadquarterAddress().getStreetAddress(), entity.getHeadquarterAddress().getStreetAddress());
        assertEquals(cs.getHeadquarterAddress().getLocality(), entity.getHeadquarterAddress().getLocality());
        assertEquals(cs.getHeadquarterAddress().getPostalCode(), entity.getHeadquarterAddress().getPostalCode());

        assertEquals(cs.getLegalRegistrationNumber().getEori(), entity.getLegalRegistrationNumber().getEori());
        assertEquals(cs.getLegalRegistrationNumber().getVatID(), entity.getLegalRegistrationNumber().getVatID());
        assertEquals(cs.getLegalRegistrationNumber().getLeiCode(), entity.getLegalRegistrationNumber().getLeiCode());
    }

    @Test
    void mapParticipantEntityToParticipantRegistrationRequestBe() {

        // given
        ParticipantRegistrationRequestEntity entity = getExampleParticipantEntity();

        // when
        ParticipantRegistrationRequestBE be = participantRegistrationServiceMapper.entityToParticipantRegistrationRequestBe(
            entity);

        // then
        assertNotNull(be);
        assertEquals(entity.getName(), be.getName());
        assertEquals(entity.getDescription(), be.getDescription());
        assertEquals(entity.getEmailAddress(), be.getEmailAddress());
        assertEquals(entity.getStatus().name(), be.getStatus().name());
        assertEquals(entity.getVpLink(), be.getVpLink());

        assertEquals(entity.getLegalRegistrationNumber().getEori(), be.getLegalRegistrationNumber().getEori());
        assertEquals(entity.getLegalRegistrationNumber().getVatID(), be.getLegalRegistrationNumber().getVatID());
        assertEquals(entity.getLegalRegistrationNumber().getLeiCode(), be.getLegalRegistrationNumber().getLeiCode());

        assertEquals(entity.getLegalAddress().getCountryCode(), be.getLegalAddress().getCountryCode());
        assertEquals(entity.getLegalAddress().getCountrySubdivisionCode(),
            be.getLegalAddress().getCountrySubdivisionCode());
        assertEquals(entity.getLegalAddress().getStreetAddress(), be.getLegalAddress().getStreetAddress());
        assertEquals(entity.getLegalAddress().getLocality(), be.getLegalAddress().getLocality());
        assertEquals(entity.getLegalAddress().getPostalCode(), be.getLegalAddress().getPostalCode());

        assertEquals(entity.getHeadquarterAddress().getCountryCode(), be.getHeadquarterAddress().getCountryCode());
        assertEquals(entity.getHeadquarterAddress().getCountrySubdivisionCode(),
            be.getHeadquarterAddress().getCountrySubdivisionCode());
        assertEquals(entity.getHeadquarterAddress().getStreetAddress(), be.getHeadquarterAddress().getStreetAddress());
        assertEquals(entity.getHeadquarterAddress().getLocality(), be.getHeadquarterAddress().getLocality());
        assertEquals(entity.getHeadquarterAddress().getPostalCode(), be.getHeadquarterAddress().getPostalCode());

        assertEquals(entity.getOmejdnConnectorCertificate().getClientId(),
            be.getOmejdnConnectorCertificate().getClientId());
        assertEquals(entity.getOmejdnConnectorCertificate().getClientName(),
            be.getOmejdnConnectorCertificate().getClientName());
        assertEquals(entity.getOmejdnConnectorCertificate().getKeystore(),
            be.getOmejdnConnectorCertificate().getKeystore());
        assertEquals(entity.getOmejdnConnectorCertificate().getPassword(),
            be.getOmejdnConnectorCertificate().getPassword());
        assertEquals(entity.getOmejdnConnectorCertificate().getScope(), be.getOmejdnConnectorCertificate().getScope());

        assertEquals(entity.getDidData().getDid(), be.getDidData().getDid());
        assertEquals(entity.getDidData().getVerificationMethod(), be.getDidData().getVerificationMethod());

    }

    @Test
    void mapCertificateBeToEntity() {
        // given
        OmejdnConnectorCertificateBE certificate = getExampleCertificateBe();

        // when
        OmejdnConnectorCertificateEntity entity = participantRegistrationServiceMapper.omejdnConnectorCertificateBEToOmejdnConnectorCertificateEntity(
            certificate);

        // then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(certificate.getClientId(), entity.getClientId());
        assertEquals(certificate.getClientName(), entity.getClientName());
        assertEquals(certificate.getScope(), entity.getScope());
        assertEquals(certificate.getKeystore(), entity.getKeystore());
        assertEquals(certificate.getPassword(), entity.getPassword());
    }

    private PxExtendedLegalParticipantCredentialSubject getExampleParticipantCs() {

        GxVcard address = new GxVcard();
        address.setStreetAddress("Example Street 123");
        address.setLocality("Berlin");
        address.setPostalCode("12345");
        address.setCountryCode("DE");
        address.setCountrySubdivisionCode("DE-BE");

        GxNestedLegalRegistrationNumberCredentialSubject registrationNumber = new GxNestedLegalRegistrationNumberCredentialSubject();
        registrationNumber.setEori("EORI");
        registrationNumber.setVatID("VATID");
        registrationNumber.setLeiCode("LEI");

        PxExtendedLegalParticipantCredentialSubject cs = new PxExtendedLegalParticipantCredentialSubject();
        cs.setId("id");
        cs.setName("name");
        cs.setDescription("description");
        cs.setMailAddress("mailAddress");
        cs.setLegalRegistrationNumber(registrationNumber);
        cs.setLegalAddress(address);
        cs.setHeadquarterAddress(address);

        return cs;
    }

    private ParticipantRegistrationRequestEntity getExampleParticipantEntity() {

        VcardEntity address = new VcardEntity();
        address.setStreetAddress("Example Street 123");
        address.setLocality("Berlin");
        address.setPostalCode("12345");
        address.setCountryCode("DE");
        address.setCountrySubdivisionCode("DE-BE");

        RegistrationNumberEntity registrationNumber = new RegistrationNumberEntity();
        registrationNumber.setId(111L);
        registrationNumber.setEori("EORI");
        registrationNumber.setVatID("VATID");
        registrationNumber.setLeiCode("LEI");

        OmejdnConnectorCertificateEntity certificate = new OmejdnConnectorCertificateEntity();
        certificate.setId(222L);
        certificate.setClientId("11:22:33");
        certificate.setClientName("clientName");
        certificate.setScope("scope");
        certificate.setKeystore("keystore");
        certificate.setPassword("password");

        DidDataEntity didData = new DidDataEntity();
        didData.setId(333L);
        didData.setDid("did");
        didData.setVerificationMethod("verificationMethod");

        ParticipantRegistrationRequestEntity entity = new ParticipantRegistrationRequestEntity();
        entity.setId(1234L);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        entity.setName("name");
        entity.setDescription("description");
        entity.setEmailAddress("mailAddress");
        entity.setLegalRegistrationNumber(registrationNumber);
        entity.setLegalAddress(address);
        entity.setHeadquarterAddress(address);

        entity.setOmejdnConnectorCertificate(certificate);
        entity.setDidData(didData);

        entity.setStatus(RequestStatus.COMPLETED);
        entity.setVpLink("vpLink");

        return entity;
    }

    private OmejdnConnectorCertificateBE getExampleCertificateBe() {

        OmejdnConnectorCertificateBE certificate = new OmejdnConnectorCertificateBE();
        certificate.setClientId("11:22:33");
        certificate.setClientName("clientName");
        certificate.setScope("scope");
        certificate.setKeystore("keystore");
        certificate.setPassword("password");
        return certificate;
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ParticipantRegistrationEntityMapper participantRegistrationEntityMapper() {

            return Mappers.getMapper(ParticipantRegistrationEntityMapper.class);
        }

    }
}