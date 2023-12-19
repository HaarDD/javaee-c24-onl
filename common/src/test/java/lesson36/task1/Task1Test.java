package lesson36.task1;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    @Test
    public void testWordMultiple() {
        Map<String, Boolean> result1 = Task1.wordMultiple("a", "b", "a", "c", "b");
        assertEquals(true, result1.get("a"));
        assertEquals(true, result1.get("b"));
        assertEquals(false, result1.get("c"));

        Map<String, Boolean> result2 = Task1.wordMultiple("c", "b", "a");
        assertEquals(false, result2.get("a"));
        assertEquals(false, result2.get("b"));
        assertEquals(false, result2.get("c"));

        Map<String, Boolean> result3 = Task1.wordMultiple("c", "c", "c", "c");
        assertEquals(true, result3.get("c"));
    }

    @Test
    public void testMapToString() {
        Map<String, Boolean> map = Task1.wordMultiple("a", "b", "a", "c", "b");
        String result = Task1.mapToString(map);
        assertEquals("{a:true, b:true, c:false}", result);

        map = Task1.wordMultiple("c", "b", "a");
        result = Task1.mapToString(map);
        assertEquals("{a:false, b:false, c:false}", result);

        map = Task1.wordMultiple("c", "c", "c");
        result = Task1.mapToString(map);
        assertEquals("{c:true}", result);

        map = new HashMap<>();
        result = Task1.mapToString(map);
        assertEquals("{}", result);
    }
}