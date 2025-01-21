package eu.possiblex.portal.business.entity.exception;

public class RegistrationRequestConflictException extends RuntimeException {
    public RegistrationRequestConflictException(String message) {

        super(message);
    }

    public RegistrationRequestConflictException(String message, Exception e) {

        super(message, e);
    }
}