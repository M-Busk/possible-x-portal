package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ParticipantRegistrationRequestRepository
    extends JpaRepository<ParticipantRegistrationRequestEntity, Long> {
}
