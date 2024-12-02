package eu.possiblex.portal.business.entity.exception;

public class RegistrationRequestException extends RuntimeException {
    public RegistrationRequestException(String message) {

        super(message);
    }

    public RegistrationRequestException(String message, Exception e) {

        super(message, e);
    }
}