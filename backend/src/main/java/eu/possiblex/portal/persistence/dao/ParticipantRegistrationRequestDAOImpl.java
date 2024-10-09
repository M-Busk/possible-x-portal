package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
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
     * @param metadata registration request metadata
     */
    @Transactional
    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant, ParticipantMetadataBE metadata) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationEntityMapper.pxExtendedLegalParticipantCsAndMetadataToNewEntity(
            participant, metadata);
        log.info("Saving participant registration request: {}", entity);

        participantRegistrationRequestRepository.save(entity);
    }

    /**
     * Get a list of all participant registration requests.
     *
     * @return list of participant registration requests
     */
    @Override
    public List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests() {

        log.info("Getting all participant registration requests");
        return participantRegistrationRequestRepository.findAll().stream()
            .map(participantRegistrationEntityMapper::entityToParticipantRegistrationRequestBe).toList();
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
    public void completeRegistrationRequest(String id) {
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            entity.setStatus(RequestStatus.COMPLETED);
            log.info("Completing participant registration request: {}", id);
            participantRegistrationRequestRepository.save(entity);
        } else {
            log.error("(Complete) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    @Override
    public void storeRegistrationRequestVpLink(String id, String vpLink) {
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            entity.setVpLink(vpLink);
            log.info("Storing the VP Link: {}", vpLink);
            //participantRegistrationRequestRepository.save(entity);
        } else {
            log.error("(Set VP Link) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    @Override
    public void storeRegistrationRequestDaps(String id, OmejdnConnectorCertificateBE certificate) {
        OmejdnConnectorCertificateEntity certificateEntity =
            participantRegistrationEntityMapper.omjednConnectorCertificateBEToOmejdnConnectorCertificateEntity(certificate);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            entity.setStatus(RequestStatus.COMPLETED);
            entity.setOmejdnConnectorCertificate(certificateEntity);
            log.info("Storing the OmejdnConnectorCertificate: {}", certificateEntity);
            //participantRegistrationRequestRepository.save(entity);
        } else {
            log.error("(Set Daps) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    /**
     * Given an existing registration request, store the corresponding DID data.
     *
     * @param id registration request id
     * @param to DID data to store
     */
    @Transactional
    @Override
    public void storeRegistrationRequestDid(String id, ParticipantDidBE to) {
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            DidDataEntity didData = new DidDataEntity();
            didData.setDid(to.getDid());
            didData.setVerificationMethod(to.getVerificationMethod());
            entity.setDidData(didData);
            log.info("Storing the DidDataEntity: {}", didData);
        } else {
            log.error("(Set Did) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }
}
