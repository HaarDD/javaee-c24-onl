package by.teachmeskills.lesson26.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ValidationResponse {

    @JsonIgnore
    boolean isValid();
}
