package eu.possiblex.portal.persistence.dao;


import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;


import java.util.List;

public class ParticipantRegistrationRequestDAOFake implements ParticipantRegistrationRequestDAO {

    @Override
    public List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests() {

        return List.of();
    }

    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject participant, ParticipantMetadataBE metadata) {
        // request worked
    }

    @Override
    public void acceptRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void rejectRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void deleteRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void completeRegistrationRequest(String id) {

    }
}
