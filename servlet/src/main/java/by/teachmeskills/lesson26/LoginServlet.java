package by.teachmeskills.lesson26;

import by.teachmeskills.lesson26.dao.UserDAO;
import by.teachmeskills.lesson26.dto.UserDTO;
import by.teachmeskills.lesson26.validation.ValidationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.teachmeskills.lesson26.services.DataUtilsOLD.readJsonFromRequest;


@Slf4j
//@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/lesson26/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Пришло на логин");
        HttpSession httpSession = request.getSession();

        request.setCharacterEncoding("UTF-8");

        String json = readJsonFromRequest(request);

        ObjectMapper objectMapper = new ObjectMapper();

        UserDTO.UserLoginValidationRequest userLoginValidationRequest = objectMapper.readValue(json, UserDTO.UserLoginValidationRequest.class);
        System.out.println(json);
        ValidationResponse userLoginValidationResponse = userLoginValidationRequest.getValidationResponse();

        response.setContentType("application/json");
        response.getWriter().print(objectMapper.writeValueAsString(userLoginValidationResponse));
        System.out.println(objectMapper.writeValueAsString(userLoginValidationResponse));
        log.info(objectMapper.writeValueAsString(userLoginValidationResponse));

        if (userLoginValidationResponse.isValid()) {
            httpSession = request.getSession(true);
            httpSession.setAttribute("user", UserDAO.getUserByEmail(userLoginValidationRequest.getEmail()));

            httpSession.setMaxInactiveInterval(24 * 60 * 60);
        } else {
            log.info("Авторизация открыта для сессии {}", httpSession);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }


    }
}
