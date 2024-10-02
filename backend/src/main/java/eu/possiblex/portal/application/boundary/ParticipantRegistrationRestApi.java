package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/registration")
public interface ParticipantRegistrationRestApi {
    /**
     * POST request for sending a participant registration request, processing it and storing it for later use.
     *
     * @param request participant registration request
     */
    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    void registerParticipant(@RequestBody CreateRegistrationRequestTO request);

    /**
     * GET request for retrieving all registration requests.
     *
     * @return list of registration requests
     */
    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    List<RegistrationRequestEntryTO> getAllRegistrationRequests();

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