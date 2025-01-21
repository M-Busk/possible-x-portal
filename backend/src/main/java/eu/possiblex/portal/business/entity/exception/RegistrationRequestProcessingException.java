package eu.possiblex.portal.business.entity.exception;

public class RegistrationRequestProcessingException extends RuntimeException {
    public RegistrationRequestProcessingException(String message) {

        super(message);
    }

    public RegistrationRequestProcessingException(String message, Exception e) {

        super(message, e);
    }
}