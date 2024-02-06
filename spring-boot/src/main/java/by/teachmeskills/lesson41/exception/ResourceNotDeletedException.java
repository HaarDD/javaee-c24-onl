package by.teachmeskills.lesson41.exception;

public class ResourceNotDeletedException extends RuntimeException {

    public ResourceNotDeletedException(String message) {
        super(message);
    }

    public ResourceNotDeletedException(String message, Object resource) {
        super("%s, Resource: %s ".formatted(message, resource));
    }
}
