package eu.possiblex.portal.application.entity.credentials.px.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.possiblex.portal.application.entity.credentials.PojoCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.serialization.StringDeserializer;
import eu.possiblex.portal.application.entity.credentials.serialization.StringSerializer;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "type", "@context" }, allowGetters = true)
@NoArgsConstructor
@AllArgsConstructor
public class PxParticipantExtensionCredentialSubject extends PojoCredentialSubject {
    @Getter(AccessLevel.NONE)
    public static final String TYPE_NAMESPACE = "px";

    @Getter(AccessLevel.NONE)
    public static final String TYPE_CLASS = "PossibleXLegalParticipantExtension";

    @Getter(AccessLevel.NONE)
    public static final String TYPE = TYPE_NAMESPACE + ":" + TYPE_CLASS;

    @Getter(AccessLevel.NONE)
    public static final Map<String, String> CONTEXT = Map.of(TYPE_NAMESPACE, "http://w3id.org/gaia-x/possible-x#",
        "vcard", "http://www.w3.org/2006/vcard/ns#", "xsd", "http://www.w3.org/2001/XMLSchema#");

    @JsonProperty("px:mailAddress")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String mailAddress;

    @JsonProperty("type")
    public String getType() {

        return TYPE;
    }

    @JsonProperty("@context")
    public Map<String, String> getContext() {

        return CONTEXT;
    }
}
