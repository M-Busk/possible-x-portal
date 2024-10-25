package eu.possiblex.portal.persistence.control;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.persistence.entity.DidDataEntity;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import eu.possiblex.portal.persistence.entity.RegistrationNumberEntity;
import eu.possiblex.portal.persistence.entity.VcardEntity;
import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationEntityMapper {

    @Mapping(target = "id", ignore = true)
    VcardEntity gxVcardToEntity(GxVcard vcard);

    @Mapping(target = "id", ignore = true)
    RegistrationNumberEntity gxLegalRegistrationNumberToEntity(
        GxLegalRegistrationNumberCredentialSubject registrationNumberCs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(eu.possiblex.portal.persistence.entity.RequestStatus.NEW)")
    @Mapping(target = "emailAddress", source = "mailAddress")
    ParticipantRegistrationRequestEntity pxExtendedLegalParticipantCsToNewEntity(
        PxExtendedLegalParticipantCredentialSubject cs);

    GxVcard entityToGxVcard(VcardEntity entity);

    @Mapping(target = "id", ignore = true)
    GxLegalRegistrationNumberCredentialSubject entityToGxLegalRegistrationNumber(RegistrationNumberEntity entity);

    ParticipantDidBE entityToParticipantDidBe(DidDataEntity entity);

    ParticipantRegistrationRequestBE entityToParticipantRegistrationRequestBe(
        ParticipantRegistrationRequestEntity entity);

    OmejdnConnectorCertificateEntity omjednConnectorCertificateBEToOmejdnConnectorCertificateEntity(
        OmejdnConnectorCertificateBE omjednConnectorCertificateBE);
}
