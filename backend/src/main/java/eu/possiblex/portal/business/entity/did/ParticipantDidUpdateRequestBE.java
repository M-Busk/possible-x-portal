package eu.possiblex.portal.business.entity.did;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDidUpdateRequestBE {
    private String did;

    private List<String> aliases;
}
