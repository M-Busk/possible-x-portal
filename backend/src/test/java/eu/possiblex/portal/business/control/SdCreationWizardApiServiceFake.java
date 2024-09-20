package eu.possiblex.portal.business.control;

import java.util.List;
import java.util.Map;

public class SdCreationWizardApiServiceFake implements SdCreationWizardApiService {
    /**
     * Return the map of shape-type:filename for a given ecosystem.
     *
     * @param ecosystem ecosystem to filter for
     * @return map of filenames as described above
     */
    @Override
    public Map<String, List<String>> getShapesByEcosystem(String ecosystem) {

        return Map.of("Participant", List.of("Legalparticipant.json", "Legalregistrationnumber.json"));
    }

    @Override
    public List<String> getParticipantShapesByEcosystem(String ecosystem) {

        return List.of("Legalparticipant.json", "Legalregistrationnumber.json");
    }

    /**
     * Given a JSON file name, return the corresponding JSON shape file
     *
     * @param ecosystem ecosystem of shape
     * @param jsonName JSON file name
     * @return JSON file
     */
    @Override
    public String getShapeByName(String ecosystem, String jsonName) {

        return """
            {
                "someKey": "someValue"
            }
            """;
    }
}
