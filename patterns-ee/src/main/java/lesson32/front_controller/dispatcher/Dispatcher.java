package lesson32.front_controller.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Dispatcher {
    void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
