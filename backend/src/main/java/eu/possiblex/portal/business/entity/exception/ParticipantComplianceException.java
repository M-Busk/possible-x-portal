package eu.possiblex.portal.business.entity.exception;

public class ParticipantComplianceException extends RuntimeException {

    public ParticipantComplianceException(String message) {

        super(message);
    }

    public ParticipantComplianceException(String message, Throwable cause) {

        super(message, cause);
    }
}
