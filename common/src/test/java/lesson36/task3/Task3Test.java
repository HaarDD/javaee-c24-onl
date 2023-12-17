package lesson36.task3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task3Test {

    @Test
    void checkBraces() {
        assertTrue(Task3.checkBraces("()", Task3.TYPES_BRACES));
        assertTrue(Task3.checkBraces("[()]", Task3.TYPES_BRACES));
        assertTrue(Task3.checkBraces("{[()]}", Task3.TYPES_BRACES));
        assertTrue(Task3.checkBraces("([{{[(())]}}])", Task3.TYPES_BRACES));

        assertFalse(Task3.checkBraces("{{[]()}}}}", Task3.TYPES_BRACES));
        assertFalse(Task3.checkBraces("{[(])}", Task3.TYPES_BRACES));
    }
}