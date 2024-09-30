package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.AddressTO;
import eu.possiblex.portal.application.entity.RegistrationNumberTO;
import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationServiceMapper {

    AddressTO gxVcardToAddressTO(GxVcard gxVcard);

    RegistrationNumberTO legalRegistrationNumberToRegistrationNumberTO(
        GxLegalRegistrationNumberCredentialSubject legalRegistrationNumber);

    RegistrationRequestListTO pxExtendedLegalParticipantCsToRegistrationRequestListTO(
        PxExtendedLegalParticipantCredentialSubject pxExtendedLegalparticipantCredentialSubject);
}
