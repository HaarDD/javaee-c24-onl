package by.teachmeskills.lesson26.validation;

import java.util.List;
import java.util.regex.Pattern;

public class RequestValidatorOLD {

    public static final Pattern REGEX_PATTERN_NAME = Pattern.compile("^[A-Za-zА-Яа-я]+$");

    public static boolean isClientFirstNameValid(String clientFirstName) {
        return REGEX_PATTERN_NAME.matcher(clientFirstName).find();
    }

    public static boolean isClientLastNameValid(String clientLastName) {
        return REGEX_PATTERN_NAME.matcher(clientLastName).find();
    }

    public static boolean isClientAddressValid(String clientAddress) {
        return !clientAddress.trim().isEmpty();
    }

    public static boolean isClientPersonalDataAgreeValid(Boolean clientPersonalDataAgree) {
        return clientPersonalDataAgree;
    }

    public static boolean isClientServiceValid(List<String> clientService) {
        if (clientService == null) return false;
        return !clientService.isEmpty();
    }

}
