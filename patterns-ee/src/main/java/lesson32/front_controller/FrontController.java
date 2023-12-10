package lesson32.front_controller;

import lesson32.front_controller.dispatcher.ControllerRegistry;
import lesson32.front_controller.dispatcher.Dispatcher;
import lesson32.front_controller.dispatcher.DispatcherControllersService;
import lesson32.front_controller.service.PropertiesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/nav/*")
public class FrontController extends HttpServlet {
    private static final String CONTROLLER_CONFIGURATION_FILENAME = "controllers.properties";
    private Dispatcher dispatcher;

    @Override
    public void init() throws ServletException {
        dispatcher = new DispatcherControllersService();

        Properties controllersMap = PropertiesService.getLoadedProperties(CONTROLLER_CONFIGURATION_FILENAME, getClass().getClassLoader());

        ControllerRegistry.registerAllByPropertiesMap(controllersMap);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcher.dispatch(request, response);
    }

}
