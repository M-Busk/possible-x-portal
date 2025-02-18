package eu.possiblex.portal.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressTO {
    @Schema(description = "Country code as an ISO 3166-1 alpha2, alpha-3 or numeric format value", example = "DE")
    private String countryCode;

    @Schema(description = "Country subdivision code as an ISO 3166-2 format value", example = "DE-NI")
    private String countrySubdivisionCode;

    @Schema(description = "Street address", example = "Some Street 123")
    private String streetAddress;

    @Schema(description = "Locality", example = "Some City")
    private String locality;

    @Schema(description = "Postal Code", example = "12345")
    private String postalCode;
}
