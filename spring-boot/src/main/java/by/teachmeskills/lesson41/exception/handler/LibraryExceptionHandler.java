package by.teachmeskills.lesson41.exception.handler;

import by.teachmeskills.lesson41.exception.ResourceAlreadyExistException;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.exception.message.ErrorMessage;
import by.teachmeskills.lesson41.exception.message.ValidationErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "Ресурс не найден!");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> resourceAlreadyExistException(ResourceAlreadyExistException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                "Ресурс уже существует!");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> resourceNotCreatedException(ResourceNotCreatedException ex){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                "Ресурс не был создан!");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorMessage> handleValidationExceptions(BindException ex) {
        ValidationErrorMessage validationErrorMessage = new ValidationErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "Resource: %s".formatted(ex.getTarget()),
                "Ресурс не прошел валидацию!",
                getFieldErrors(ex));
        return new ResponseEntity<>(validationErrorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleOtherExceptions(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                "Ошибка сервера!");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, String> getFieldErrors(BindException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        return fieldErrors;
    }


}
