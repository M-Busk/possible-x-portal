package eu.possiblex.portal.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Table(name = "participant_registration_request")
public class ParticipantRegistrationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Column(unique = true)
    @NotNull
    private String name;

    @Size(max = 1023)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registration_number_id", referencedColumnName = "id")
    @NotNull
    private RegistrationNumberEntity legalRegistrationNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "legal_address_id", referencedColumnName = "id")
    @NotNull
    private VcardEntity legalAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "headquarter_address_id", referencedColumnName = "id")
    @NotNull
    private VcardEntity headquarterAddress;

    @NotNull
    private RequestStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "did_data_id", referencedColumnName = "id")
    private DidDataEntity didData;

    @NotNull
    private String emailAddress;
}
