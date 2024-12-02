package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.entity.DidDataEntity;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import eu.possiblex.portal.persistence.entity.RequestStatus;
import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class ParticipantRegistrationRequestDAOImpl implements ParticipantRegistrationRequestDAO {
    private final ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    private final ParticipantRegistrationEntityMapper participantRegistrationEntityMapper;

    public ParticipantRegistrationRequestDAOImpl(
        @Autowired ParticipantRegistrationRequestRepository participantRegistrationRequestRepository,
        @Autowired ParticipantRegistrationEntityMapper participantRegistrationEntityMapper) {

        this.participantRegistrationRequestRepository = participantRegistrationRequestRepository;
        this.participantRegistrationEntityMapper = participantRegistrationEntityMapper;
    }

    /**
     * Initially save a participant registration request.
     *
     * @param participant registration request CS
     */
    @Transactional
    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationEntityMapper.pxExtendedLegalParticipantCsToNewEntity(
            participant);
        log.info("Saving participant registration request: {}", entity);

        participantRegistrationRequestRepository.save(entity);
    }

    /**
     * Get a list of all participant registration requests.
     *
     * @return list of participant registration requests
     */
    @Override
    public List<ParticipantRegistrationRequestBE> getAllRegistrationRequests() {

        log.info("Getting all participant registration requests");
        return participantRegistrationRequestRepository.findAll().stream()
            .map(participantRegistrationEntityMapper::entityToParticipantRegistrationRequestBe).toList();
    }

    @Override
    public ParticipantRegistrationRequestBE getRegistrationRequestByDid(String did) {

        log.info("Getting participant registration request by did");

        return participantRegistrationEntityMapper.entityToParticipantRegistrationRequestBe(
            participantRegistrationRequestRepository.findByDidData_Did(did));
    }

    /**
     * Accept a participant registration request given the id, if the current status allows so.
     *
     * @param id registration request id
     */
    @Transactional
    @Override
    public void acceptRegistrationRequest(String id) {

        log.info("Accepting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            if (entity.getStatus() == RequestStatus.COMPLETED) {
                log.error("Cannot accept completed participant registration request: {}", id);
                throw new RuntimeException("Cannot accept completed participant registration request: " + id);
            } else {
                entity.setStatus(RequestStatus.ACCEPTED);
            }
        } else {
            log.error("(Accept) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    /**
     * Reject a participant registration request given the id, if the current status allows so.
     *
     * @param id registration request id
     */
    @Transactional
    @Override
    public void rejectRegistrationRequest(String id) {

        log.info("Rejecting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            if (entity.getStatus() == RequestStatus.COMPLETED) {
                log.error("Cannot reject completed participant registration request: {}", id);
                throw new RuntimeException("Cannot reject completed participant registration request: " + id);
            } else {
                entity.setStatus(RequestStatus.REJECTED);
            }
        } else {
            log.error("(Reject) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    /**
     * Delete a participant registration request given the id, if the current status allows so.
     *
     * @param id registration request id
     */
    @Transactional
    @Override
    public void deleteRegistrationRequest(String id) {

        log.info("Deleting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            if (entity.getStatus() == RequestStatus.COMPLETED) {
                log.error("Cannot delete completed participant registration request: {}", id);
                throw new RuntimeException("Cannot delete completed participant registration request: " + id);
            } else {
                participantRegistrationRequestRepository.delete(entity);
            }
        } else {
            log.error("(Delete) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    /**
     * Complete a participant registration request given the id.
     *
     * @param id registration request id
     */
    @Transactional
    @Override
    public void completeRegistrationRequest(String id, ParticipantDidBE did, String vpLink,
        OmejdnConnectorCertificateBE certificate) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            log.info("Completing participant registration request: {}", id);

            // set did data
            DidDataEntity didData = new DidDataEntity();
            didData.setDid(did.getDid());
            didData.setVerificationMethod(did.getVerificationMethod());
            entity.setDidData(didData);

            // set daps data
            OmejdnConnectorCertificateEntity certificateEntity = participantRegistrationEntityMapper.omejdnConnectorCertificateBEToOmejdnConnectorCertificateEntity(
                certificate);
            entity.setOmejdnConnectorCertificate(certificateEntity);

            // set vp link
            entity.setVpLink(vpLink);

            // complete request
            entity.setStatus(RequestStatus.COMPLETED);
        } else {
            log.error("(Complete) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Override
    public ParticipantRegistrationRequestBE getRegistrationRequestByName(String name) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(name);
        if (entity != null) {
            return participantRegistrationEntityMapper.entityToParticipantRegistrationRequestBe(entity);
        } else {
            return null;
        }
    }
}
