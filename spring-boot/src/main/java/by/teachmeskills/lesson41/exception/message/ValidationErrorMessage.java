package by.teachmeskills.lesson41.exception.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorMessage extends ErrorMessage {

    private Map<String, String> fieldErrors;

    public ValidationErrorMessage(int statusCode, Date timestamp, String message, String description, Map<String, String> fieldErrors) {
        super(statusCode, timestamp, message, description);
        this.fieldErrors = fieldErrors;
    }
}
