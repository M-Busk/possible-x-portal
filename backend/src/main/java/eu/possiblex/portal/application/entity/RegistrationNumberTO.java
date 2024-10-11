package eu.possiblex.portal.application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationNumberTO {

    private String eori;

    private String vatID;

    private String leiCode;
}
