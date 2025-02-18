package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.EnvironmentTO;
import eu.possiblex.portal.application.entity.VersionTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/common")
public interface CommonPortalRestApi {
    @Operation(summary = "Get the version of the portal", tags = {
        "Common" }, description = "Get the version of the portal.")
    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    VersionTO getVersion();

    @Operation(summary = "Get the environment of the portal", tags = {
        "Common" }, description = "Get the environment of the portal.")
    @GetMapping(value = "/environment", produces = MediaType.APPLICATION_JSON_VALUE)
    EnvironmentTO getEnvironment();
}
