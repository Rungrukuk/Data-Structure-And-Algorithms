package DataStructureAndAlgorithms.exceptions;

public abstract class InstantiationFailureException extends RuntimeException {
    public InstantiationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}