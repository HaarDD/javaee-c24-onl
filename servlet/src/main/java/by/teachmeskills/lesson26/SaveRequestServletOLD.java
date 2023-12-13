package by.teachmeskills.lesson26;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import by.teachmeskills.lesson26.validation.RequestValidationResponseOLD;
import com.fasterxml.jackson.databind.ObjectMapper;

import by.teachmeskills.lesson26.dto.RepairRequestOLD;

import static by.teachmeskills.lesson26.services.DataUtilsOLD.*;


@WebServlet(urlPatterns = "/save-request")
public class SaveRequestServletOLD extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SaveRequestServletOLD.class);

    public static final String ATTRIBUTE_REPAIR_REQUEST = "repair_request";

    static {
        System.setProperty("log4jFileName", "save_request_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
    }

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

        RepairRequestOLD repairRequest = objectMapper.readValue(json, RepairRequestOLD.class);

        repairRequest.setRequestSessionId(httpSession.getId());

        RequestValidationResponseOLD requestValidationResponse = repairRequest.getRequestValidationResponse();

        response.setContentType("application/json");
        response.getWriter().print(objectMapper.writeValueAsString(requestValidationResponse));

        if (!requestValidationResponse.isRequestValidationResponseValid()) {
            LOGGER.info("Реквест (введенные данные) невалидный!\n" + requestValidationResponse + "\n" + json);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<RepairRequestOLD> repairRequestList = getListFromAttributeDataSession(httpSession, ATTRIBUTE_REPAIR_REQUEST, RepairRequestOLD.class);

        if (repairRequestList == null) {
            repairRequestList = new ArrayList<>();
            LOGGER.info("Клиент впервые оставил заявку!\n" + json + "\n" + httpSession.getId());
        }

        repairRequestList.add(repairRequest);

        httpSession.setAttribute(ATTRIBUTE_REPAIR_REQUEST, repairRequestList);

        LOGGER.info("Реквест успешно сохранен!\n" + requestValidationResponse + "\n" + json);
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
