package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ParticipantRegistrationServiceImpl implements ParticipantRegistrationService {
    @Override
    public void registerParticipant(PossibleParticipantBE be) {

        log.info("Registering participant: {}", be);
    }
}
