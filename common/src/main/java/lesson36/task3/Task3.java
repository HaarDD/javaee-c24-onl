package lesson36.task3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Task3 {

    public static Map<Character, Character> TYPES_BRACES = Map.of(
            '(', ')',
            '[', ']',
            '{', '}'
    );

    public static boolean checkBraces(String input, Map<Character, Character> bracesMap) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char currentChar : input.toCharArray()) {
            if (bracesMap.containsValue(currentChar)) {
                if (stack.isEmpty() || bracesMap.get(stack.pop()) != currentChar) {
                    return false;
                }
            } else if (bracesMap.containsKey(currentChar)) {
                stack.push(currentChar);
            }
        }
        return stack.isEmpty();
    }
}
