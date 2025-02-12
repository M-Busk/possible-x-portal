package eu.possiblex.portal.persistence.control;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(eu.possiblex.portal.persistence.entity.RequestStatus.NEW)")
    @Mapping(target = "emailAddress", source = "mailAddress")
    ParticipantRegistrationRequestEntity pxExtendedLegalParticipantCsToNewEntity(
        PxExtendedLegalParticipantCredentialSubject cs);

    ParticipantRegistrationRequestBE entityToParticipantRegistrationRequestBe(
        ParticipantRegistrationRequestEntity entity);

    OmejdnConnectorCertificateEntity omejdnConnectorCertificateBEToOmejdnConnectorCertificateEntity(
        OmejdnConnectorCertificateBE omjednConnectorCertificateBE);
}
