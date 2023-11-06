package by.teachmeskills.lesson26;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import by.teachmeskills.lesson26.dto.RepairRequest;
import org.json.JSONObject;

@WebServlet(urlPatterns = "/save-request")
public class SaveRequestServlet extends HttpServlet {


    public static final String PARAMETER_FIRST_NAME = "client-first-name";
    public static final String PARAMETER_LAST_NAME = "client-last-name";
    public static final String PARAMETER_ADDRESS = "client-address";
    public static final String PARAMETER_PERSONAL_DATA_AGREE = "client-personal-data-agree";
    public static final String PARAMETER_SELECTED_SERVICE = "client-service";
    public static final Pattern REGEX_PATTERN_NAME = Pattern.compile("^[A-Za-zА-Яа-я]+$");
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
        request.getRequestDispatcher("lesson26/save-request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        request.setCharacterEncoding("UTF-8");
        String json = readJsonFromRequest(request);

        JSONObject formData = new JSONObject(json);

        JSONObject validationJson = new JSONObject();

        String firstName = formData.getString(PARAMETER_FIRST_NAME);
        String lastName = formData.getString(PARAMETER_LAST_NAME);
        String address = formData.getString(PARAMETER_ADDRESS);
        List<String> serviceList = formData.getJSONArray(PARAMETER_SELECTED_SERVICE).toList().stream().map(Object::toString).toList();

        boolean isFirstNameValid = REGEX_PATTERN_NAME.matcher(firstName).find();
        boolean isLastNameValid = REGEX_PATTERN_NAME.matcher(lastName).find();
        boolean isAddressValid = !address.isEmpty();
        boolean isPersonalDataValid = formData.getBoolean(PARAMETER_PERSONAL_DATA_AGREE);
        boolean isServiceValid = !serviceList.isEmpty();

        validationJson.put(PARAMETER_FIRST_NAME, isFirstNameValid);
        validationJson.put(PARAMETER_LAST_NAME, isLastNameValid);
        validationJson.put(PARAMETER_ADDRESS, isAddressValid);
        validationJson.put(PARAMETER_PERSONAL_DATA_AGREE, isPersonalDataValid);
        validationJson.put(PARAMETER_SELECTED_SERVICE, isServiceValid);

        response.setContentType("application/json");
        response.getWriter().print(validationJson);
        if (isFirstNameValid && isLastNameValid && isAddressValid && isPersonalDataValid && isServiceValid) {

            List<RepairRequest> repairRequestList = (List<RepairRequest>) httpSession.getAttribute("repair_request");

            if (repairRequestList == null) {
                repairRequestList = new ArrayList<>();
            }

            repairRequestList.add(new RepairRequest(httpSession.getId(), firstName, lastName, address, serviceList));

            httpSession.setAttribute("repair_request", repairRequestList);

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String readJsonFromRequest(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            return reader.readLine();
        }
    }

}
