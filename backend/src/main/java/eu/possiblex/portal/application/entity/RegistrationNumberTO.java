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
public class RegistrationNumberTO {
    @Schema(description = "EORI", example = "DE123456789012345")
    private String eori;

    @Schema(description = "VAT ID", example = "DE269448547")
    private String vatID;

    @Schema(description = "LEI Code", example = "391200RT75XV0TG47X87")
    private String leiCode;
}
