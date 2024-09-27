package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParticipantRegistrationServiceImpl implements ParticipantRegistrationService {

    private final ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    private final ParticipantRegistrationServiceMapper participantRegistrationServiceMapper;

    public ParticipantRegistrationServiceImpl(
        @Autowired ParticipantRegistrationRequestDAO participantRegistrationRequestDAO,
        @Autowired ParticipantRegistrationServiceMapper participantRegistrationServiceMapper) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
        this.participantRegistrationServiceMapper = participantRegistrationServiceMapper;
    }

    @Override
    public void registerParticipant(PossibleParticipantBE be) {

        log.info("Processing participant registration: {}", be);

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(be);
    }

    @Override
    public List<RegistrationRequestListTO> getAllParticipantRegistrationRequests() {

        log.info("Processing retrieval of all participant registration requests");

        return participantRegistrationRequestDAO.getAllParticipantRegistrationRequests().stream().map(
            participantRegistrationServiceMapper::possibleParticipantBEToRegistrationRequestListTO).toList();
    }
}
