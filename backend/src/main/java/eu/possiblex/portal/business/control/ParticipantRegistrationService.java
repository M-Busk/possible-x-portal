package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipantRegistrationService {

    /**
     * Given a registration request, process and store it for later use.
     *
     * @param cs registration request
     */
    void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs);

    /**
     * Get registration requests for a given pagination request.
     *
     * @return TO with list of registration requests
     */
    Page<RegistrationRequestEntryTO> getParticipantRegistrationRequests(Pageable paginationRequest);

    /**
     * Get a registration request by did
     *
     * @param did DID
     * @return registration request
     */
    RegistrationRequestEntryTO getParticipantRegistrationRequestByDid(String did);

    /**
     * Given a registration request id, accept the registration request.
     *
     * @param id registration request id
     */
    void acceptRegistrationRequest(String id);

    /**
     * Given a registration request id, reject the registration request.
     *
     * @param id registration request id
     */
    void rejectRegistrationRequest(String id);

    /**
     * Given a registration request id, delete the registration request.
     *
     * @param id registration request id
     */
    void deleteRegistrationRequest(String id);
}
