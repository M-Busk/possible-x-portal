package eu.possiblex.portal.business.entity.credentials.px;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PxExtendedLegalParticipantCredentialSubject {

    @Getter(AccessLevel.NONE)
    public static final List<String> TYPE = List.of(GxLegalParticipantCredentialSubject.TYPE,
        "px:PossibleXLegalParticipantExtension");

    @Getter(AccessLevel.NONE)
    public static final Map<String, String> CONTEXT = Map.of(GxLegalParticipantCredentialSubject.TYPE_NAMESPACE,
        "https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#",
        "vcard", "http://www.w3.org/2006/vcard/ns#", "xsd", "http://www.w3.org/2001/XMLSchema#", "px",
        "http://w3id.org/gaia-x/possible-x#");

    private String id;

    @NotNull
    @JsonProperty("gx:legalRegistrationNumber")
    private GxLegalRegistrationNumberCredentialSubject legalRegistrationNumber;

    @NotNull
    @JsonProperty("gx:legalAddress")
    private GxVcard legalAddress;

    @NotNull
    @JsonProperty("gx:headquarterAddress")
    private GxVcard headquarterAddress;

    @JsonProperty("gx:name")
    private String name;

    @JsonProperty("gx:description")
    private String description;

    @JsonProperty("@type")
    public List<String> getType() {

        return TYPE;
    }

    @JsonProperty("@context")
    public Map<String, String> getContext() {

        return CONTEXT;
    }
}
