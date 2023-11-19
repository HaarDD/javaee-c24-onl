package by.teachmeskills.lesson26.dto;

import by.teachmeskills.lesson26.tag.IgnoreField;
import by.teachmeskills.lesson26.validation.RequestValidationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import static by.teachmeskills.lesson26.validation.RequestValidator.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairRequest {

    @IgnoreField
    public static final Map<String, String> REPAIR_REQUEST_FIELDS_CYR_NAMES = Map.of(
            "requestSessionId", "Id сессии",
            "clientFirstName", "Имя",
            "clientLastName", "Фамилия",
            "clientAddress", "Адрес",
            "clientPersonalDataAgree", "Согласие",
            "clientService", "Услуги"

    );
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