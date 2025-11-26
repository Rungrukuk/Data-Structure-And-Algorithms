package DataStructureAndAlgorithms.exceptions;

public class ProblemInstantiationException extends InstantiationFailureException {
    public ProblemInstantiationException(String className, Throwable cause) {
        super("Failed to instantiate problem: " + className, cause);
    }
}
