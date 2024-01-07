package by.teachmeskills.lesson41.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Object resource) {
        super("%s, Resource: %s ".formatted(message, resource));
    }
}
