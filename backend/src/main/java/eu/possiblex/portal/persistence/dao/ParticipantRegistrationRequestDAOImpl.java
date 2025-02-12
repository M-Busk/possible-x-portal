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
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityNotFoundException;
import eu.possiblex.portal.persistence.entity.exception.ParticipantEntityStateTransitionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class ParticipantRegistrationRequestDAOImpl implements ParticipantRegistrationRequestDAO {
    private static final List<String> VALID_ENTITY_SORT_FIELDS = List.of("name", "status");

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
    public Page<ParticipantRegistrationRequestBE> getRegistrationRequests(Pageable paginationRequest) {

        List<Sort.Order> orders = paginationRequest.getSort().stream()
            .filter(o -> VALID_ENTITY_SORT_FIELDS.contains(o.getProperty())).toList();

        Pageable entityPageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(),
            Sort.by(orders));
        log.info("Getting participant registration requests for parsed pagination request: {}", entityPageable);

        Page<ParticipantRegistrationRequestEntity> page = participantRegistrationRequestRepository.findAll(
            entityPageable);
        return page.map(participantRegistrationEntityMapper::entityToParticipantRegistrationRequestBe);
    }

    @Override
    public ParticipantRegistrationRequestBE getRegistrationRequestByDid(String did) {

        log.info("Getting participant registration request by did");

        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByDidData_Did(did);

        if (entity == null) {
            return null;
        }

        return participantRegistrationEntityMapper.entityToParticipantRegistrationRequestBe(entity);
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

        ParticipantRegistrationRequestEntity entity = findParticipantRegistrationRequestById(id);

        if (entity.getStatus() == RequestStatus.COMPLETED) {
            log.error("Cannot accept completed participant registration request: {}", id);
            throw new ParticipantEntityStateTransitionException(
                "Cannot accept completed participant registration request: " + id);
        }

        if (entity.getStatus() == RequestStatus.REJECTED) {
            log.error("Cannot accept rejected participant registration request: {}", id);
            throw new ParticipantEntityStateTransitionException(
                "Cannot accept rejected participant registration request: " + id);
        }

        entity.setStatus(RequestStatus.ACCEPTED);
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

        ParticipantRegistrationRequestEntity entity = findParticipantRegistrationRequestById(id);

        if (entity.getStatus() == RequestStatus.COMPLETED) {
            log.error("Cannot reject completed participant registration request: {}", id);
            throw new ParticipantEntityStateTransitionException(
                "Cannot reject completed participant registration request: " + id);
        }

        entity.setStatus(RequestStatus.REJECTED);
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

        ParticipantRegistrationRequestEntity entity = findParticipantRegistrationRequestById(id);

        if (entity.getStatus() == RequestStatus.COMPLETED) {
            log.error("Cannot delete completed participant registration request: {}", id);
            throw new ParticipantEntityStateTransitionException(
                "Cannot delete completed participant registration request: " + id);
        }

        participantRegistrationRequestRepository.delete(entity);
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

        log.info("Completing participant registration request: {}", id);

        ParticipantRegistrationRequestEntity entity = findParticipantRegistrationRequestById(id);

        if (entity.getStatus() != RequestStatus.ACCEPTED) {
            log.error("Cannot complete non-accepted participant registration request: {}", id);
            throw new ParticipantEntityStateTransitionException(
                "Cannot complete non-accepted participant registration request: " + id);
        }

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
    }

    @Override
    public ParticipantRegistrationRequestBE getRegistrationRequestByName(String name) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(name);

        if (entity == null) {
            return null;
        }

        return participantRegistrationEntityMapper.entityToParticipantRegistrationRequestBe(entity);
    }

    private ParticipantRegistrationRequestEntity findParticipantRegistrationRequestById(String id) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity == null) {
            log.error("Participant with id {} not found", id);
            throw new ParticipantEntityNotFoundException("Participant not found: " + id);
        }

        return entity;
    }

}
