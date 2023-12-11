package by.teachmeskills.lesson26.services;

import by.teachmeskills.lesson26.dto.RepairRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataUtilsTest {

    @Test
    void testGetListFromAttribute_ValidList() {
        List<String> testData = Arrays.asList("item1", "item2", "item3");

        List<String> result = DataUtils.getListFromAttribute(testData, String.class);

        assertNotNull(result);
        assertEquals(testData, result);
    }

    @Test
    void testGetRequestValidationResponse() {
        RepairRequest repairRequest = new RepairRequest(
                "12345678910",
                "Иван",
                "Иванов",
                "Минск, Уручье",
                true,
                Arrays.asList("КОД1", "КОД2")
        );

        List<RepairRequest> testData = Arrays.asList(repairRequest, repairRequest);

        List<RepairRequest> result = DataUtils.getListFromAttribute(testData, RepairRequest.class);

        assertEquals(testData, result);
    }


}