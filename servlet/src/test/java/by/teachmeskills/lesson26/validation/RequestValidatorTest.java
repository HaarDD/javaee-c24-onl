package by.teachmeskills.lesson26.validation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestValidatorTest {

    @Test
    void testClientFirstNameValid() {
        assertTrue(RequestValidatorOLD.isClientFirstNameValid("John"));
        assertTrue(RequestValidatorOLD.isClientFirstNameValid("Иван"));
        assertFalse(RequestValidatorOLD.isClientFirstNameValid(""));
        assertFalse(RequestValidatorOLD.isClientFirstNameValid("123"));
    }

    @Test
    void testClientLastNameValid() {
        assertTrue(RequestValidatorOLD.isClientLastNameValid("Doe"));
        assertTrue(RequestValidatorOLD.isClientLastNameValid("Петров"));
        assertFalse(RequestValidatorOLD.isClientLastNameValid(""));
        assertFalse(RequestValidatorOLD.isClientLastNameValid("123"));
    }

    @Test
    void testClientAddressValid() {
        assertTrue(RequestValidatorOLD.isClientAddressValid("123 Main St"));
        assertFalse(RequestValidatorOLD.isClientAddressValid(""));
        assertFalse(RequestValidatorOLD.isClientAddressValid("   "));
    }

    @Test
    void testClientPersonalDataAgreeValid() {
        assertTrue(RequestValidatorOLD.isClientPersonalDataAgreeValid(true));
        assertFalse(RequestValidatorOLD.isClientPersonalDataAgreeValid(false));
    }

    @Test
    void testClientServiceValid() {
        List<String> validServices = Arrays.asList("Service1", "Service2");
        List<String> emptyServices = new ArrayList<>();
        List<String> nullServices = null;

        assertTrue(RequestValidatorOLD.isClientServiceValid(validServices));
        assertFalse(RequestValidatorOLD.isClientServiceValid(emptyServices));
        assertFalse(RequestValidatorOLD.isClientServiceValid(nullServices));
    }
}