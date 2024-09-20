package eu.possiblex.portal.application.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/shapes")
public interface ParticipantShapeRestApi {
    /**
     * GET request for retrieving the Gaia-X service offering shape.
     *
     * @return catalog shape
     */
    @GetMapping("/gx/legalparticipant")
    String getGxLegalParticipantShape();

    @GetMapping("/gx/legalregistrationnumber")
    String getGxLegalRegistrationNumberShape();
}
