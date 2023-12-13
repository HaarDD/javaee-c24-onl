package by.teachmeskills.lesson26.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DataUtilsOLD<T> {

    public static String readJsonFromRequest(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            return reader.readLine();
        }
    }

    public static <T> List<T> getListFromAttributeDataSession(HttpSession session, String attributeName, Class<T> attributeListClass) {
        Object attributeValue = session.getAttribute(attributeName);
        return getListFromAttribute(attributeValue, attributeListClass);
    }

    public static <T> List<T> getListFromAttribute(Object attributeValue, Class<T> attributeListClass) {
        if (attributeValue instanceof List<?> rawList) {
            for (Object item : rawList) {
                if (!attributeListClass.isInstance(item)) {
                    return null;
                }
            }
            return (List<T>) rawList;
        }
        return null;
    }

}
