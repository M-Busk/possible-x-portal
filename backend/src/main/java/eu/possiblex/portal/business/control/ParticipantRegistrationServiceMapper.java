package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationServiceMapper {

    RegistrationRequestEntryTO participantRegistrationRequestBEToRegistrationRequestEntryTO(
        ParticipantRegistrationRequestBE participantRegistrationRequestBE);

    @Mapping(target = "id", source = "didData.did")
    @Mapping(target = "mailAddress", source = "emailAddress")
    PxExtendedLegalParticipantCredentialSubject participantRegistrationRequestBEToCs(
        ParticipantRegistrationRequestBE participantRegistrationRequestBE);

}
