package by.teachmeskills.lesson26;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.teachmeskills.lesson26.validation.RequestValidationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import by.teachmeskills.lesson26.dto.RepairRequest;

import static by.teachmeskills.lesson26.services.DataUtils.*;

@WebServlet(urlPatterns = "/save-request")
public class SaveRequestServlet extends HttpServlet {
    public static final String ATTRIBUTE_REPAIR_REQUEST = "repair_request";

    public static final Map<String, String> SERVICE_LIST = Map.of(
            "R_TP", "Замена термопасты",
            "R_CL", "Чистка",
            "R_CPU", "Замена процессора",
            "R_GPU", "Замена видеочипа",
            "R_RAM", "Замена памяти",
            "R_KEYS", "Восстановление клавиатуры",
            "R_CYR", "Нанесение кириллицы",
            "R_SYS", "Установка системы");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/lesson26/save-request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        request.setCharacterEncoding("UTF-8");

        String json = readJsonFromRequest(request);

        ObjectMapper objectMapper = new ObjectMapper();

        RepairRequest repairRequest = objectMapper.readValue(json, RepairRequest.class);

        RequestValidationResponse requestValidationResponse = repairRequest.getRequestValidationResponse();

        response.setContentType("application/json");
        response.getWriter().print(objectMapper.writeValueAsString(requestValidationResponse));

        if (!requestValidationResponse.isRequestValidationResponseValid()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<RepairRequest> repairRequestList = getListFromAttributeDataSession(httpSession, ATTRIBUTE_REPAIR_REQUEST, RepairRequest.class);

        if (repairRequestList == null) {
            repairRequestList = new ArrayList<>();
        }

        repairRequestList.add(repairRequest);

        httpSession.setAttribute(ATTRIBUTE_REPAIR_REQUEST, repairRequestList);

        response.setStatus(HttpServletResponse.SC_OK);

    }


}
