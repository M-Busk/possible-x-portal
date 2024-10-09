package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;

import java.util.List;

public class ParticipantRegistrationServiceFake implements ParticipantRegistrationService {
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs, ParticipantMetadataBE be) {
        // request worked
    }

    @Override
    public List<RegistrationRequestEntryTO> getAllParticipantRegistrationRequests() {

        return List.of();
    }

    @Override
    public void acceptRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void rejectRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void deleteRegistrationRequest(String id) {
        // request worked
    }
}
