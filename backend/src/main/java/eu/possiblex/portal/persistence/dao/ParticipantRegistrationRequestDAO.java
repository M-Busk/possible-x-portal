package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipantRegistrationRequestDAO {
    /**
     * Save a participant registration request.
     *
     * @param participant registration request CS
     */
    void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant);

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

    void completeRegistrationRequest(String id, ParticipantDidBE did, String vpLink,
        OmejdnConnectorCertificateBE certificate);

    /**
     * Get a list of registration requests for given pageable.
     *
     * @return list of registration requests
     */
    Page<ParticipantRegistrationRequestBE> getRegistrationRequests(Pageable paginationRequest);

    /**
     * Get a registration request by DID.
     *
     * @param did DID
     * @return registration request
     */
    ParticipantRegistrationRequestBE getRegistrationRequestByDid(String did);

    ParticipantRegistrationRequestBE getRegistrationRequestByName(String name);
}
