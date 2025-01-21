package eu.possiblex.portal.business.entity.exception;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(String message) {

        super(message);
    }
}