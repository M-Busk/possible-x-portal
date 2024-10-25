package eu.possiblex.portal.business.entity.did;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDidBE {
    private String did;

    private String verificationMethod;
}
