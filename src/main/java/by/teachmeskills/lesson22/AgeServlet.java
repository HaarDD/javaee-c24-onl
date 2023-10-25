package by.teachmeskills.lesson22;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import by.teachmeskills.lesson22.util.AgeUtils;

@WebServlet(urlPatterns = {"/age", "/age_result"})
public class AgeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/age.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result;
        try {
            result = AgeUtils.checkAdult(Double.parseDouble(request.getParameter("age"))) ? "You are an adult!" : "You are underage!";
        } catch (NumberFormatException | NullPointerException e) {
            result = "Invalid input data!";
        }
        response.getWriter().println(result);
    }
}


