package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.AddressTO;
import eu.possiblex.portal.application.entity.ParticipantDidDataTO;
import eu.possiblex.portal.application.entity.RegistrationNumberTO;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationServiceMapper {

    AddressTO gxVcardToAddressTO(GxVcard gxVcard);

    RegistrationNumberTO legalRegistrationNumberToRegistrationNumberTO(
        GxLegalRegistrationNumberCredentialSubject legalRegistrationNumber);

    ParticipantDidDataTO didDataToDidDataTO(ParticipantDidBE didData);

    RegistrationRequestEntryTO participantRegistrationRequestBEToRegistrationRequestEntryTO(
        ParticipantRegistrationRequestBE participantRegistrationRequestBE);

}
