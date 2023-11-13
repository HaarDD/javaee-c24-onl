package by.teachmeskills.lesson26;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.teachmeskills.lesson26.SaveRequestServlet.SERVICE_LIST;
import static by.teachmeskills.lesson26.dto.RepairRequest.REPAIR_REQUEST_FIELDS_CYR_NAMES;

@WebServlet(urlPatterns = "/print-request")
public class PrintRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().setAttribute("fieldsTranslator", REPAIR_REQUEST_FIELDS_CYR_NAMES);
        getServletContext().setAttribute("listElementsTranslator", SERVICE_LIST);
        request.getRequestDispatcher("WEB-INF/lesson26/print-request.jsp").forward(request, response);
    }
}
