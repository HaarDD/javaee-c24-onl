package lesson36.task2;

import lesson36.task1.Task1;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    @Test
    void wordFirstLastLetter() {
        Map<Character, Character> result1 = Task2.wordFirstLastLetter("code", "bug");
        assertEquals('g', result1.get('b'));
        assertEquals('e', result1.get('c'));
        assertEquals(Task1.mapToString(result1), "{b:g, c:e}");

        Map<Character, Character> result2 = Task2.wordFirstLastLetter("man", "moon", "main");
        assertEquals('n', result2.get('m'));
        assertEquals(Task1.mapToString(result2), "{m:n}");

        Map<Character, Character> result3 = Task2.wordFirstLastLetter("man", "moon", "good", "night");
        assertEquals('d', result3.get('g'));
        assertEquals('n', result3.get('m'));
        assertEquals('t', result3.get('n'));
        assertEquals(Task1.mapToString(result3), "{g:d, m:n, n:t}");
    }
}