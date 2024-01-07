package by.teachmeskills.lesson41.exception;

public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException(String message) {
        super(message);
    }

    public ResourceAlreadyExistException(String message, Object resource) {
        super("%s, Resource: %s ".formatted(message, resource));
    }

}
