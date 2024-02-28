package by.teachmeskills.lesson41.controller.auth;

import by.teachmeskills.lesson41.security.service.JwtHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static by.teachmeskills.lesson41.security.JwtFilter.JWT_COOKIE_NAME;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiController {

    public static final String AUTH_REQUEST_MAPPING = "/auth";

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtHelper jwtHelper;

    @Value("#{new Integer('${cookie.authorization-max-age}')}")
    private Integer authorizationMaxAge;

    @GetMapping(AUTH_REQUEST_MAPPING)
    public ResponseEntity<String> login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    (UsernamePasswordAuthenticationToken) daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(login, password));

            String jwt = jwtHelper.generateToken(AuthApiController.class.getSimpleName(), usernamePasswordAuthenticationToken);

            Cookie cookie = new Cookie(JWT_COOKIE_NAME, jwt);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(authorizationMaxAge);
            response.addCookie(cookie);

            log.info("Успешная авторизация для пользователя: {}", login);
            return ResponseEntity.ok("Авторизация прошла успешно");
        } catch (Exception e) {
            log.error("Ошибка аутентификации: {}", login, e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

}
