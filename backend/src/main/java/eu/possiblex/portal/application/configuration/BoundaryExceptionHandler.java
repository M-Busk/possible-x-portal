package eu.possiblex.portal.application.configuration;

import eu.possiblex.portal.application.entity.ErrorResponseTO;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestConflictException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

/**
 * Exception handler for boundary exceptions that should be passed to the frontend.
 */
@RestControllerAdvice
@Slf4j
public class BoundaryExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle exceptions that occur when a registration request conflicts with an existing one.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleException(RegistrationRequestConflictException e) {

        logError(e);
        return new ResponseEntity<>(new ErrorResponseTO("Failed to create registration request", e.getMessage()),
            CONFLICT);
    }

    /**
     * Handle exceptions that occur during the processing of registration requests (e.g. issues during daps/did
     * creation).
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleException(RegistrationRequestProcessingException e) {

        logError(e);
        return new ResponseEntity<>(new ErrorResponseTO("Failed to process registration request", e.getMessage()),
            UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle compliance exceptions for participant self descriptions.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleException(ParticipantComplianceException e) {

        logError(e);
        return new ResponseEntity<>(
            new ErrorResponseTO("Compliance was not attested for this participant", e.getMessage()),
            UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle all other exceptions.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleException(Exception e) {

        logError(e);
        return new ResponseEntity<>(new ErrorResponseTO("An unknown error occurred"), INTERNAL_SERVER_ERROR);
    }

    private void logError(Exception e) {

        log.error("Caught boundary exception: {}", e.getClass().getName(), e);
    }
}
