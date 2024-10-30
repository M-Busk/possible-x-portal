package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.business.control.SdCreationWizardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ParticipantShapeRestApiImpl implements ParticipantShapeRestApi {

    private static final String ECOSYSTEM_GAIAX = "gx";

    private static final String ECOSYSTEM_POSSIBLE = "px";

    private final SdCreationWizardApiService sdCreationWizardApiService;

    public ParticipantShapeRestApiImpl(@Autowired SdCreationWizardApiService sdCreationWizardApiService) {

        this.sdCreationWizardApiService = sdCreationWizardApiService;
    }

    /**
     * Retrieve the Gaia-X legal participant shape from the SD Creation Wizard API and return it.
     *
     * @return catalog shape
     */
    @Override
    public String getGxLegalParticipantShape() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_GAIAX, "Legalparticipant.json");
    }

    /**
     * Retrieve the Gaia-X legal registration number shape from the SD Creation Wizard API and return it.
     *
     * @return catalog shape
     */
    @Override
    public String getGxLegalRegistrationNumberShape() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_GAIAX, "Legalregistrationnumber.json");
    }

    /**
     * Retrieve the Possible-X participant extension shape from the SD Creation Wizard API and return it.
     *
     * @return catalog shape
     */
    @Override
    public String getPxParticipantExtension() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_POSSIBLE, "Possiblexlegalparticipantextension.json");
    }
}
