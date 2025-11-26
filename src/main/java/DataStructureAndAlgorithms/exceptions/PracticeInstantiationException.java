package DataStructureAndAlgorithms.exceptions;

public class PracticeInstantiationException extends InstantiationFailureException {
    public PracticeInstantiationException(String className, Throwable cause) {
        super("Failed to instantiate practice: " + className, cause);
    }
} 
