package eu.possiblex.portal.application.control;

import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantCredentialMapper {

    @Mapping(target = "legalRegistrationNumber", source = "registrationNumberCs", qualifiedByName = "registrationNumberMapping")
    @Mapping(target = "id", source = "participantCs.id")
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "context", ignore = true)
    PossibleParticipantBE credentialSubjectsToBE(GxLegalParticipantCredentialSubject participantCs,
        GxLegalRegistrationNumberCredentialSubject registrationNumberCs);

    @Named("registrationNumberMapping")
    default List<GxLegalRegistrationNumberCredentialSubject> registrationNumberMapping(
        GxLegalRegistrationNumberCredentialSubject registrationNumberCs) {

        return List.of(registrationNumberCs);
    }
}

