package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;

import java.util.List;

public interface ParticipantRegistrationRequestDAO {
    void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject request);

    void acceptRegistrationRequest(String id);

    void rejectRegistrationRequest(String id);

    void deleteRegistrationRequest(String id);

    void completeRegistrationRequest(String id);

    List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests();

}
