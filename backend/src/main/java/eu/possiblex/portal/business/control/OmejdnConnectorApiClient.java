package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateDto;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface OmejdnConnectorApiClient {

    @PostExchange("/add")
    OmejdnConnectorCertificateDto addConnector(@RequestBody OmejdnConnectorCertificateRequest request);
}
