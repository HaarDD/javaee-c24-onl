package by.teachmeskills.lesson26.validation;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestValidationResponse {

    private Boolean clientFirstName;
    private Boolean clientLastName;
    private Boolean clientAddress;
    private Boolean clientPersonalDataAgree;
    private Boolean clientService;

    @JsonIgnore
    public Boolean isRequestValidationResponseValid() {
        return clientFirstName && clientLastName && clientAddress && clientPersonalDataAgree && clientService;
    }

}
