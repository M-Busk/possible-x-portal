package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.CatalogCommunicationException;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;

public class FhCatalogClientFake implements FhCatalogClient {

    public static final String FAILING_PARTICIPANT_ID = "failingParticipantId";

    public static final String BAD_COMPLIANCE_PARTICIPANT_ID = "badComplianceParticipantId";

    @Override
    public FhCatalogIdResponse addParticipantToCatalog(PxExtendedLegalParticipantCredentialSubject cs) {

        if (cs.getId().equals(FAILING_PARTICIPANT_ID)) {
            throw new CatalogCommunicationException("Failing participant", new Exception());
        }

        if (cs.getId().equals(BAD_COMPLIANCE_PARTICIPANT_ID)) {
            throw new ParticipantComplianceException("Bad compliance participant", new Exception());
        }

        return new FhCatalogIdResponse(cs.getId());
    }

    @Override
    public PxExtendedLegalParticipantCredentialSubject getParticipantFromCatalog(String participantId) {

        return null;
    }

    @Override
    public void deleteParticipantFromCatalog(String participantId) throws ParticipantNotFoundException {
        // request worked
    }
}
