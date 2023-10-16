package by.teachmeskills.lesson21.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {

    public static final Pattern REGEX_URL_QUERIES = Pattern.compile("(?<name>[^?=&]+)(?>=(?<value>[^&]*))?");

    public static Map<String, String> parseUriQueryParams(String queryParam) {
        Map<String, String> paramMap = new HashMap<>();
        Matcher matcher = REGEX_URL_QUERIES.matcher(queryParam);
        while (matcher.find()) {
            String name = matcher.group("name");
            String value = matcher.group("value");
            if (!name.isEmpty() && !value.isEmpty()) {
                paramMap.put(name, value);
            }
        }
        return paramMap;
    }
}
