package by.teachmeskills.lesson41.exception;

public class ResourceNotEditedException extends RuntimeException {

    public ResourceNotEditedException(String message) {
        super(message);
    }

    public ResourceNotEditedException(String message, Object resource) {
        super("%s, Resource: %s ".formatted(message, resource));
    }
}
