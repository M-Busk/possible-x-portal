package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestConflictException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

public class ParticipantRegistrationServiceFake implements ParticipantRegistrationService {

    public static final String CONFLICT_NAME = "conflict";
    public static final String PROCESSING_ERROR_NAME = "processing-error";
    public static final String BAD_COMPLIANCE_NAME = "bad-compliance";



    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {

        switch (cs.getName()) {
            case CONFLICT_NAME -> throw new RegistrationRequestConflictException("Conflict");
            case PROCESSING_ERROR_NAME -> throw new RegistrationRequestProcessingException("Processing error");
            case BAD_COMPLIANCE_NAME -> throw new ParticipantComplianceException("Bad compliance");
            default -> {
                // request worked
            }
        }
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
