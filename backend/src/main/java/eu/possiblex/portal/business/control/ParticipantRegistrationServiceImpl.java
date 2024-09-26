package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ParticipantRegistrationServiceImpl implements ParticipantRegistrationService {

    private final ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    public ParticipantRegistrationServiceImpl(
        @Autowired ParticipantRegistrationRequestDAO participantRegistrationRequestDAO) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
    }

    @Override
    public void registerParticipant(PossibleParticipantBE be) {

        log.info("Processing participant registration: {}", be);

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(be);
    }
}
