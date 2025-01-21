package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;

public interface FhCatalogClient {

    FhCatalogIdResponse addParticipantToCatalog(PxExtendedLegalParticipantCredentialSubject cs);

    PxExtendedLegalParticipantCredentialSubject getParticipantFromCatalog(String participantId);

    void deleteParticipantFromCatalog(String participantId);
}
