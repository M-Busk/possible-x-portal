package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

public class ParticipantRegistrationServiceFake implements ParticipantRegistrationService {
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {
        // request worked
    }

    @Override
    public Page<RegistrationRequestEntryTO> getParticipantRegistrationRequests(Pageable paginationRequest) {

        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public RegistrationRequestEntryTO getParticipantRegistrationRequestByDid(String did) {

        return null;
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
