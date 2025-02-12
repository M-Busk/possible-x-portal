package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.GxNestedLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationRestApiMapper {

    @Mapping(target = "legalRegistrationNumber", source = "registrationNumberCs")
    @Mapping(target = "legalAddress", source = "participantCs.legalAddress")
    @Mapping(target = "headquarterAddress", source = "participantCs.headquarterAddress")
    @Mapping(target = "name", source = "participantCs.name")
    @Mapping(target = "description", source = "participantCs.description")
    @Mapping(target = "mailAddress", source = "participantExtensionCs.mailAddress")
    @Mapping(target = "id", ignore = true)
    PxExtendedLegalParticipantCredentialSubject credentialSubjectsToExtendedLegalParticipantCs(
        CreateRegistrationRequestTO request);
}

