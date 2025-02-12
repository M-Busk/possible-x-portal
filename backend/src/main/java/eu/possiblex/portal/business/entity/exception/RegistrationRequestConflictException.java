package eu.possiblex.portal.business.entity.exception;

public class RegistrationRequestConflictException extends RuntimeException {
    public RegistrationRequestConflictException(String message) {

        super(message);
    }
}