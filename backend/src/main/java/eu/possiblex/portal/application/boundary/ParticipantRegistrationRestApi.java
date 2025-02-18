package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/registration")
public interface ParticipantRegistrationRestApi {
    @Operation(summary = "Send a participant registration request", tags = {
        "Registration" }, description = "Send a participant registration request.")
    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    void registerParticipant(@Valid @RequestBody CreateRegistrationRequestTO request);

    @Operation(summary = "Get registration requests", tags = {
        "Registration" }, description = "Get registration requests for the given pagination request.")
    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RegistrationRequestEntryTO> getRegistrationRequests(
        @PageableDefault(sort = { "name" }) Pageable paginationRequest);

    @Operation(summary = "Get a specific registration request", tags = {
        "Registration" }, description = "Get a specific registration request by DID.", parameters = {
        @Parameter(name = "did", description = "The DID for which to get the registration request.", example = "did:web:example.com:participant:someorgltd") })
    @GetMapping(value = "/request/{did}", produces = MediaType.APPLICATION_JSON_VALUE)
    RegistrationRequestEntryTO getRegistrationRequestByDid(@PathVariable String did);

    @Operation(summary = "Accept a registration request", tags = {
        "Registration" }, description = "Accept a registration request given the ID of the registration request. In this case, the ID is the name of the participant.", parameters = {
        @Parameter(name = "id", description = "The ID of the registration request.", example = "Some Organization Ltd.") })
    @PostMapping(value = "/request/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    void acceptRegistrationRequest(@PathVariable String id);

    @Operation(summary = "Reject a registration request", tags = {
        "Registration" }, description = "Reject a registration request given the ID of the registration request. In this case, the ID is the name of the participant.", parameters = {
        @Parameter(name = "id", description = "The ID of the registration request.", example = "Some Organization Ltd.") })
    @PostMapping(value = "/request/{id}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    void rejectRegistrationRequest(@PathVariable String id);

    @Operation(summary = "Delete a registration request", tags = {
        "Registration" }, description = "Delete a registration request given the ID of the registration request. In this case, the ID is the name of the participant.", parameters = {
        @Parameter(name = "id", description = "The ID of the registration request.", example = "Some Organization Ltd.") })
    @DeleteMapping(value = "/request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteRegistrationRequest(@PathVariable String id);

}