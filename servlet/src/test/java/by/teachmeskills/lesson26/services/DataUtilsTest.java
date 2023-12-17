package by.teachmeskills.lesson26.services;

import by.teachmeskills.lesson26.dto.RepairRequestOLD;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataUtilsTest {

    @Test
    void testGetListFromAttribute_ValidList() {
        List<String> testData = Arrays.asList("item1", "item2", "item3");

        List<String> result = DataUtilsOLD.getListFromAttribute(testData, String.class);

        assertNotNull(result);
        assertEquals(testData, result);
    }

    @Test
    void testGetRequestValidationResponse() {
        RepairRequestOLD repairRequest = new RepairRequestOLD(
                "12345678910",
                "Иван",
                "Иванов",
                "Минск, Уручье",
                true,
                Arrays.asList("КОД1", "КОД2")
        );

        List<RepairRequestOLD> testData = Arrays.asList(repairRequest, repairRequest);

        List<RepairRequestOLD> result = DataUtilsOLD.getListFromAttribute(testData, RepairRequestOLD.class);

        assertEquals(testData, result);
    }


}