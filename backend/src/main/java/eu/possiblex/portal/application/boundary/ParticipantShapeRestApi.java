package eu.possiblex.portal.application.boundary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/shapes")
public interface ParticipantShapeRestApi {
    @Operation(summary = "Get the Gaia-X legal participant shape", tags = {
        "Shapes" }, description = "Get the Gaia-X legal participant shape.", responses = {
        @ApiResponse(content = @Content(schema = @Schema(description = "Gaia-X legal participant shape as JSON string", example = "{ \"prefixList\": [], \"shapes\": [] }"))) })
    @GetMapping("/gx/legalparticipant")
    String getGxLegalParticipantShape();

    @Operation(summary = "Get the Gaia-X legal registration number shape", tags = {
        "Shapes" }, description = "Get the Gaia-X legal registration number shape.", responses = {
        @ApiResponse(content = @Content(schema = @Schema(description = "Gaia-X legal registration number shape as JSON string", example = "{ \"prefixList\": [], \"shapes\": [] }"))) })
    @GetMapping("/gx/legalregistrationnumber")
    String getGxLegalRegistrationNumberShape();

    @Operation(summary = "Get the Possible-X participant extension shape", tags = {
        "Shapes" }, description = "Get the Possible-X participant extension shape.", responses = {
        @ApiResponse(content = @Content(schema = @Schema(description = "Possible-X participant extension shape as JSON string", example = "{ \"prefixList\": [], \"shapes\": [] }"))) })
    @GetMapping("/px/participantextension")
    String getPxParticipantExtension();
}
