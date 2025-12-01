package DataStructureAndAlgorithms.core.exceptions;

public class InstantiationFailureException extends RuntimeException {
    public InstantiationFailureException(String message) {
        super(message);
    }

    public InstantiationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
