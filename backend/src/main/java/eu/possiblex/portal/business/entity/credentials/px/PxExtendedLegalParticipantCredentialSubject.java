package eu.possiblex.portal.business.entity.credentials.px;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
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
        "https://w3id.org/gaia-x/development#", "vcard", "http://www.w3.org/2006/vcard/ns#", "xsd",
        "http://www.w3.org/2001/XMLSchema#", "px", "http://w3id.org/gaia-x/possible-x#", "schema",
        "https://schema.org/");

    private String id;

    @NotNull
    @JsonProperty("gx:legalRegistrationNumber")
    private GxNestedLegalRegistrationNumberCredentialSubject legalRegistrationNumber;

    @NotNull
    @JsonProperty("gx:legalAddress")
    private GxVcard legalAddress;

    @NotNull
    @JsonProperty("gx:headquarterAddress")
    private GxVcard headquarterAddress;

    @JsonProperty("schema:name")
    private String name;

    @JsonProperty("schema:description")
    private String description;

    @JsonProperty("px:mailAddress")
    private String mailAddress;

    @JsonProperty("@type")
    public List<String> getType() {

        return TYPE;
    }

    @JsonProperty("@context")
    public Map<String, String> getContext() {

        return CONTEXT;
    }
}
