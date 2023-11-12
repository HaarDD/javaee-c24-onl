package by.teachmeskills.lesson26.dto;

import by.teachmeskills.lesson26.validation.RequestValidationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static by.teachmeskills.lesson26.validation.RequestValidator.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepairRequest {

    private String requestSessionId;

    private String clientFirstName;

    private String clientLastName;

    private String clientAddress;

    private Boolean clientPersonalDataAgree;

    private List<String> clientService;

    public RequestValidationResponse getRequestValidationResponse() {
        return new RequestValidationResponse(
                isClientFirstNameValid(clientFirstName),
                isClientLastNameValid(clientLastName),
                isClientAddressValid(clientAddress),
                isClientPersonalDataAgreeValid(clientPersonalDataAgree),
                isClientServiceValid(clientService));
    }

}
