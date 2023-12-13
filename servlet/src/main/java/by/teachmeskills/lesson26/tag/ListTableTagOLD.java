package by.teachmeskills.lesson26.tag;

import lombok.Setter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Setter
public class ListTableTagOLD extends BodyTagSupport {

    private List<?> dataList;

    private Map<String, String> fieldsTranslator = new HashMap<>();

    private Map<String, String> listElementsTranslator = new HashMap<>();

    private String tableClasses = null;

    @Override
    public int doStartTag() throws JspException {
        if (dataList == null || dataList.isEmpty()) {
            return SKIP_BODY;
        }

        try {
            JspWriter out = pageContext.getOut();
            out.println("<table%s>".formatted(tableClasses != null ? " class=\"%s\"".formatted(tableClasses) : ""));

            out.println("<tr>");
            Object firstElement = dataList.get(0);

            for (Field field : firstElement.getClass().getDeclaredFields()) {
                if (!field.isAnnotationPresent(IgnoreFieldOLD.class)) {
                    out.println("<th>" + fieldsTranslator.getOrDefault(field.getName(), field.getName()) + "</th>");
                }
            }

            out.println("</tr>");

            for (Object data : dataList) {

                out.println("<tr>");

                for (Field field : data.getClass().getDeclaredFields()) {
                    if (!field.isAnnotationPresent(IgnoreFieldOLD.class)) {
                        field.setAccessible(true);
                        Object value = getObject(data, field);
                        out.println("<td>" + value + "</td>");
                    }
                }

                out.println("</tr>");
            }

            out.println("</table>");

        } catch (IllegalAccessException | IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    private Object getObject(Object data, Field field) throws IllegalAccessException {
        Object value = field.get(data);

        if (value instanceof List<?> listValue && !listElementsTranslator.isEmpty()) {
            StringBuilder listData = new StringBuilder();
            listValue.forEach(elem -> listData.append(listElementsTranslator.getOrDefault(elem.toString(), elem.toString())).append(", "));
            value = listData.substring(0, listData.length() - 2);
        } else if (value instanceof Boolean) {
            value = (Boolean) value ? "✓" : "×";
        }

        return value;
    }
}
