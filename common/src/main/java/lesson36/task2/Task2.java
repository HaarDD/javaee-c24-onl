package lesson36.task2;

import java.util.HashMap;
import java.util.Map;

public class Task2 {

    public static Map<Character, Character> wordFirstLastLetter(String... words) {
        Map<Character, Character> map = new HashMap<>();
        for (String word : words) {
            if (!word.trim().isEmpty()) {
                char firstChar = word.charAt(0);
                char lastChar = word.trim().charAt(word.length() - 1);
                map.put(firstChar, lastChar);
            }
        }
        return map;
    }
}
