/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.possiblex.portal.persistence.entity;

import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;
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

    @Size(max=1023)
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
    @JoinColumn(name = "omejdn_connector_certificate_id", referencedColumnName = "id")
    private OmejdnConnectorCertificateEntity omejdnConnectorCertificate;

    private String vpLink;
  
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "did_data_id", referencedColumnName = "id")
    private DidDataEntity didData;

    @NotNull
    private String emailAddress;
}
