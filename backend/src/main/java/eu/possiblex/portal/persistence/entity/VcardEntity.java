package eu.possiblex.portal.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "participant_vcard")
public class VcardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String countryCode;

    @NotNull
    private String countrySubdivisionCode;

    private String streetAddress;

    private String locality;

    private String postalCode;

}
