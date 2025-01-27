package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.EnvironmentTO;
import eu.possiblex.portal.application.entity.VersionTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/common")
public interface CommonPortalRestApi {
    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    /**
     * Get the version of the participant portal.
     * @return the version of the participant portal
     */
    VersionTO getVersion();

    @GetMapping(value = "/environment", produces = MediaType.APPLICATION_JSON_VALUE)
    /**
     * Get the environment for the participant portal.
     * @return the environment of the participant portal
     */
    EnvironmentTO getEnvironment();
}
