package eu.possiblex.portal.application.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import eu.possiblex.portal.business.control.SdCreationWizardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ParticipantShapeRestApiImpl implements ParticipantShapeRestApi{

    private static final String ECOSYSTEM_GAIAX = "gx";

    private final SdCreationWizardApiService sdCreationWizardApiService;

    public ParticipantShapeRestApiImpl(@Autowired SdCreationWizardApiService sdCreationWizardApiService) {

        this.sdCreationWizardApiService = sdCreationWizardApiService;
    }

    @Override
    public String getGxLegalParticipantShape() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_GAIAX, "Legalparticipant.json");
    }

    @Override
    public String getGxLegalRegistrationNumberShape() {

        return sdCreationWizardApiService.getShapeByName(ECOSYSTEM_GAIAX, "Legalregistrationnumber.json");
    }
}
