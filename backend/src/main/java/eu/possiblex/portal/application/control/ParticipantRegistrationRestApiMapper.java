package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationRestApiMapper {

    @Mapping(target = "legalRegistrationNumber", source = "registrationNumberCs")
    @Mapping(target = "id", ignore = true)
    PxExtendedLegalParticipantCredentialSubject credentialSubjectsToExtendedLegalParticipantCs(
        GxLegalParticipantCredentialSubject participantCs,
        GxLegalRegistrationNumberCredentialSubject registrationNumberCs);

    ParticipantMetadataBE requestToParticipantMetadata(
        CreateRegistrationRequestTO request);
}

