package eu.possiblex.portal.application.configuration;

import eu.possiblex.portal.application.entity.ErrorResponseTO;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestConflictException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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
     * Handle Spring validation exceptions.
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
        @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        logError(ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        StringBuilder message = new StringBuilder();
        errors.forEach((key, value) -> message.append(key).append(": ").append(value).append("; "));

        return new ResponseEntity<>(new ErrorResponseTO("Request contained errors.", message.toString().strip()),
            BAD_REQUEST);
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
