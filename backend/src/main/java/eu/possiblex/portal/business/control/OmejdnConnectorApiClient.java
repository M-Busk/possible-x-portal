package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface OmejdnConnectorApiClient {

    @PostExchange()
    OmejdnConnectorCertificateBE addConnector(@RequestBody OmejdnConnectorCertificateRequest request);

    @DeleteExchange("/{clientId}")
    void deleteConnector(@PathVariable String clientId);
}
