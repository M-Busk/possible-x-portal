package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.PossibleParticipantBE;

public interface ParticipantRegistrationRequestDAO {
    void saveParticipantRegistrationRequest(PossibleParticipantBE request);
}
