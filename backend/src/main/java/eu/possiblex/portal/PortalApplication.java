package eu.possiblex.portal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "POSSIBLE-X Portal", description = "Service for handling participant registration requests within the POSSIBLE-X Dataspace"), tags = {
    @Tag(name = "Common", description = "API for accessing version information and environment variables of the POSSIBLE-X Portal"),
    @Tag(name = "Shapes", description = "API for accessing the Gaia-X and POSSIBLE-X shapes for participant registration request creation"),
    @Tag(name = "Registration", description = "API for creating and managing participant registration requests") })

@SpringBootApplication
public class PortalApplication {

    public static void main(String[] args) {

        SpringApplication.run(PortalApplication.class, args);
    }

}
