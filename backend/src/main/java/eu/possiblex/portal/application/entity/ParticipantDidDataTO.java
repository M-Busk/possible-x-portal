package eu.possiblex.portal.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDidDataTO {
    @Schema(description = "DID of the participant", example = "did:web:example.com:participant:someorgltd")
    private String did;
}
