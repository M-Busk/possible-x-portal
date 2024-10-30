package eu.possiblex.portal.application.boundary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/shapes")
public interface ParticipantShapeRestApi {
    /**
     * GET request for retrieving the Gaia-X legal participant shape.
     *
     * @return catalog shape
     */
    @GetMapping("/gx/legalparticipant")
    String getGxLegalParticipantShape();

    /**
     * GET request for retrieving the Gaia-X legal registration number shape.
     *
     * @return catalog shape
     */
    @GetMapping("/gx/legalregistrationnumber")
    String getGxLegalRegistrationNumberShape();

    /**
     * GET request for retrieving the Possible-X participant extension shape.
     *
     * @return catalog shape
     */
    @GetMapping("/px/participantextension")
    String getPxParticipantExtension();
}
