package lesson32.front_controller.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherControllersService implements Dispatcher {

    private static final String CONTROLLER_ERROR_URL_PATH = "/error";

    @Override
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String path = request.getPathInfo();
            HttpServlet controller = ControllerRegistry.getController(path, CONTROLLER_ERROR_URL_PATH);
            controller.service(request, response);
        } catch (Exception e) {
            throw new ServletException("Ошибка в процессе обработки реквеста: ", e);
        }
    }
}
