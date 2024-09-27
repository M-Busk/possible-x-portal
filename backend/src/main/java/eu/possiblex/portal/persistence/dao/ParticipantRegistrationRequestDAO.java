package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;

import java.util.List;

public interface ParticipantRegistrationRequestDAO {
    void saveParticipantRegistrationRequest(PossibleParticipantBE request);

    List<PossibleParticipantBE> getAllParticipantRegistrationRequests();
}
