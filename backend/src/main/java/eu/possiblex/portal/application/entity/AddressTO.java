package eu.possiblex.portal.application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressTO {
    private String countryCode;

    private String countrySubdivisionCode;

    private String streetAddress;

    private String locality;

    private String postalCode;
}
