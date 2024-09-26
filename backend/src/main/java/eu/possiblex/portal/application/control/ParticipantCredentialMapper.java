package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantCredentialMapper {

    @Mapping(target = "legalRegistrationNumber", source = "registrationNumberCs")
    @Mapping(target = "id", ignore = true)
    PossibleParticipantBE credentialSubjectsToBE(GxLegalParticipantCredentialSubject participantCs,
        GxLegalRegistrationNumberCredentialSubject registrationNumberCs);
}

