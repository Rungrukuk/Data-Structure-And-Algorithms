package DataStructureAndAlgorithms.exceptions;

public class FileCreationException extends RuntimeException {
    public FileCreationException(String message) {
        super(message);
    }

    public FileCreationException(String message, Throwable e) {
        super(message, e);
    }
}
