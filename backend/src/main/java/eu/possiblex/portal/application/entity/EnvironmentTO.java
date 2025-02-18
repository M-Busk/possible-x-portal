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
public class EnvironmentTO {
    @Schema(description = "URL of the catalog UI ", example = "https://catalog.example.com/")
    private String catalogUiUrl;
}
