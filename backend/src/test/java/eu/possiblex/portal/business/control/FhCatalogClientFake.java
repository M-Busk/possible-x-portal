package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;

public class FhCatalogClientFake implements FhCatalogClient {

    @Override
    public FhCatalogIdResponse addParticipantToCatalog(PxExtendedLegalParticipantCredentialSubject cs) {

        return new FhCatalogIdResponse("id");
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
