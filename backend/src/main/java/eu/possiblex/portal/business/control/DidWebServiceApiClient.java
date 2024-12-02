package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import eu.possiblex.portal.business.entity.did.ParticipantDidUpdateRequestBE;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface DidWebServiceApiClient {

    @PostExchange("/internal/didweb")
    ParticipantDidBE generateDidWeb(@RequestBody ParticipantDidCreateRequestBE request);

    @DeleteExchange("/internal/didweb/{did}")
    void deleteDidWeb(@PathVariable String did);

    @PatchExchange("/internal/didweb")
    void updateDidWeb(@RequestBody ParticipantDidUpdateRequestBE request);

    @GetExchange("/participant/{id}/did.json")
    String getDidDocument(@PathVariable(value = "id") String id);
}
