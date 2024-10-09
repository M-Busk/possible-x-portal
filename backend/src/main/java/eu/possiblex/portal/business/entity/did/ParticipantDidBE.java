package eu.possiblex.portal.business.entity.did;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDidBE {
    private String did;

    private String verificationMethod;
}
