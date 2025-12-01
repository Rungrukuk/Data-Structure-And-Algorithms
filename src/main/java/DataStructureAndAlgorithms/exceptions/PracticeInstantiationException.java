package DataStructureAndAlgorithms.exceptions;

import DataStructureAndAlgorithms.core.exceptions.InstantiationFailureException;

public class PracticeInstantiationException extends InstantiationFailureException {
    public PracticeInstantiationException(String className, Throwable cause) {
        super("Failed to instantiate practice: " + className, cause);
    }
} 
