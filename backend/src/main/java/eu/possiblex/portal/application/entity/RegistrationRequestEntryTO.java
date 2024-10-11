package eu.possiblex.portal.application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestEntryTO {
    private RegistrationNumberTO legalRegistrationNumber;

    private AddressTO legalAddress;

    private AddressTO headquarterAddress;

    private String name;

    private String description;

    private RequestStatus status;
}
