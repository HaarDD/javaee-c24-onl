package lesson36.task1;

import java.util.HashMap;
import java.util.Map;

public class Task1 {

    public static Map<String, Boolean> wordMultiple(String... words) {
        Map<String, Boolean> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.containsKey(word));
        }
        return map;
    }

    public static String mapToString(Map<?, ?> map) {
        StringBuilder result = new StringBuilder("{");
        map.forEach((key, value) -> result.append(key).append(":").append(value).append(", "));
        if (!map.isEmpty()) {
            result.setLength(result.length() - 2);
        }
        return result.append("}").toString();
    }
}
