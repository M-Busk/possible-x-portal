package eu.possiblex.portal.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "participant_registration_number")
public class RegistrationNumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String eori;

    private String vatID;

    private String leiCode;
}
