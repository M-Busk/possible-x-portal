package eu.possiblex.portal.persistence.control;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import eu.possiblex.portal.persistence.entity.RegistrationNumberEntity;
import eu.possiblex.portal.persistence.entity.VcardEntity;
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
    ParticipantRegistrationRequestEntity possibleParticipantBEToEntity(PossibleParticipantBE participantBE);

    GxVcard entityToGxVcard(VcardEntity entity);

    @Mapping(target = "id", ignore = true)
    GxLegalRegistrationNumberCredentialSubject entityToGxLegalRegistrationNumber(
        RegistrationNumberEntity entity);

    @Mapping(target = "id", ignore = true)
    PossibleParticipantBE entityToPossibleParticipantBE(ParticipantRegistrationRequestEntity entity);
}
