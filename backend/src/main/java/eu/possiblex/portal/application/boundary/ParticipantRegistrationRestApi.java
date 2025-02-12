package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/registration")
public interface ParticipantRegistrationRestApi {
    /**
     * POST request for sending a participant registration request, processing it and storing it for later use.
     *
     * @param request participant registration request
     */
    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    void registerParticipant(@Valid @RequestBody CreateRegistrationRequestTO request);

    /**
     * GET request for retrieving registration requests for the given pagination request.
     *
     * @return TO with list of registration requests
     */
    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RegistrationRequestEntryTO> getRegistrationRequests(
        @PageableDefault(sort = { "name" }) Pageable paginationRequest);

    /**
     * GET request for retrieving a specific registration requests by did.
     *
     * @param did DID
     * @return registration request
     */
    @GetMapping(value = "/request/{did}", produces = MediaType.APPLICATION_JSON_VALUE)
    RegistrationRequestEntryTO getRegistrationRequestByDid(@PathVariable String did);

    /**
     * POST request for accepting a registration request.
     *
     * @param id registration request id
     */
    @PostMapping(value = "/request/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    void acceptRegistrationRequest(@PathVariable String id);

    /**
     * POST request for rejecting a registration request.
     *
     * @param id registration request id
     */
    @PostMapping(value = "/request/{id}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    void rejectRegistrationRequest(@PathVariable String id);

    /**
     * DELETE request for deleting a registration request.
     *
     * @param id registration request id
     */
    @DeleteMapping(value = "/request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteRegistrationRequest(@PathVariable String id);

}