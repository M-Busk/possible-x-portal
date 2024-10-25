package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;

public interface FhCatalogClient {

    FhCatalogIdResponse addParticipantToCatalog(PxExtendedLegalParticipantCredentialSubject cs);

    PxExtendedLegalParticipantCredentialSubject getParticipantFromCatalog(String participantId)
        throws ParticipantNotFoundException;
}
