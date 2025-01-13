package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.VersionTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonPortalRestApiImpl implements CommonPortalRestApi {

    @Value("${version.no}")
    private String version;

    @Value("${version.date}")
    private String versionDate;

    @Override
    public VersionTO getVersion() {

        return new VersionTO(version, versionDate);
    }
}
