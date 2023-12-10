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
        assertTrue(RequestValidator.isClientFirstNameValid("John"));
        assertTrue(RequestValidator.isClientFirstNameValid("Иван"));
        assertFalse(RequestValidator.isClientFirstNameValid(""));
        assertFalse(RequestValidator.isClientFirstNameValid("123"));
    }

    @Test
    void testClientLastNameValid() {
        assertTrue(RequestValidator.isClientLastNameValid("Doe"));
        assertTrue(RequestValidator.isClientLastNameValid("Петров"));
        assertFalse(RequestValidator.isClientLastNameValid(""));
        assertFalse(RequestValidator.isClientLastNameValid("123"));
    }

    @Test
    void testClientAddressValid() {
        assertTrue(RequestValidator.isClientAddressValid("123 Main St"));
        assertFalse(RequestValidator.isClientAddressValid(""));
        assertFalse(RequestValidator.isClientAddressValid("   "));
    }

    @Test
    void testClientPersonalDataAgreeValid() {
        assertTrue(RequestValidator.isClientPersonalDataAgreeValid(true));
        assertFalse(RequestValidator.isClientPersonalDataAgreeValid(false));
    }

    @Test
    void testClientServiceValid() {
        List<String> validServices = Arrays.asList("Service1", "Service2");
        List<String> emptyServices = new ArrayList<>();
        List<String> nullServices = null;

        assertTrue(RequestValidator.isClientServiceValid(validServices));
        assertFalse(RequestValidator.isClientServiceValid(emptyServices));
        assertFalse(RequestValidator.isClientServiceValid(nullServices));
    }
}