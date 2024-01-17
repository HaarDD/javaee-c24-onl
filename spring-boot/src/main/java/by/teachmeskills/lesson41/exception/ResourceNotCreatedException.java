package by.teachmeskills.lesson41.exception;

public class ResourceNotCreatedException extends RuntimeException {

    public ResourceNotCreatedException(String message) {
        super(message);
    }

    public ResourceNotCreatedException(String message, Object resource) {
        super("%s, Resource: %s ".formatted(message, resource));
    }
}
