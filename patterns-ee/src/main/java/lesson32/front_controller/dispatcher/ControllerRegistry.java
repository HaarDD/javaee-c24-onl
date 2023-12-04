package lesson32.front_controller.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ControllerRegistry {

    private static final String CONTROLLERS_FOLDER = "lesson32.front_controller.controllers.";
    private static final Map<String, HttpServlet> controllers = new ConcurrentHashMap<>();

    public static void registerController(String url, String controllerPath) throws ServletException {
        try {
            controllers.put(url, initController(controllerPath));
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new ServletException("Ошибка при инициализации контроллера: ", e);
        }
    }

    public static void registerAllByPropertiesMap(Properties controllersMap) throws ServletException {
        for (Map.Entry<Object, Object> property : controllersMap.entrySet()) {
            String url = (String) property.getKey();
            String controllerName = CONTROLLERS_FOLDER + property.getValue();
            registerController(url, controllerName);
        }
    }

    public static HttpServlet getController(String url, String errorUrl) {
        if (url != null) {
            return controllers.getOrDefault(url, controllers.get(errorUrl));
        }
        return controllers.get(errorUrl);
    }

    private static HttpServlet initController(String controllerPath) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> controllerClass = Class.forName(controllerPath);
        return (HttpServlet) controllerClass.getDeclaredConstructor().newInstance();
    }

}
