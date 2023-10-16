package by.teachmeskills.lesson21.util;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    public static Map<String, String> parseUriQueryParams(String queryParam){
        Map<String, String> paramMap = new HashMap<>();
        String[] params = queryParam.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            String paramName = split[0];
            String paramValue = split[1];
            paramMap.put(paramName, paramValue);
        }
        return paramMap;
    }
}
