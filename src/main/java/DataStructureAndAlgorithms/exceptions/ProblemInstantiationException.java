package DataStructureAndAlgorithms.exceptions;

import DataStructureAndAlgorithms.core.exceptions.InstantiationFailureException;

public class ProblemInstantiationException extends InstantiationFailureException {
    public ProblemInstantiationException(String className, Throwable cause) {
        super("Failed to instantiate problem: " + className, cause);
    }
}
