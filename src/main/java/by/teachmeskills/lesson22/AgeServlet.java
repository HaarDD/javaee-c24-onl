package by.teachmeskills.lesson22;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/age", "/age_result"}, loadOnStartup = 1)
public class AgeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/age.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result;
        try {
            System.out.println(request.getParameter("age"));
            result = Double.parseDouble(request.getParameter("age")) >= 18 ? "You are an adult!" : "You are underage!";
        } catch (NumberFormatException | NullPointerException e) {
            result = "Invalid input data!";
        }
        response.getWriter().println(result);
    }
}


