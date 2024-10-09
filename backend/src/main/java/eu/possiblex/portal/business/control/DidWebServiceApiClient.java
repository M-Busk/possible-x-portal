package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface DidWebServiceApiClient {

    @PostExchange("/internal/didweb/generate")
    ParticipantDidBE generateDidWeb(@RequestBody ParticipantDidCreateRequestBE request);

    @GetExchange("/participant/{id}/did.json")
    String getDidDocument(@PathVariable(value = "id") String id);
}
