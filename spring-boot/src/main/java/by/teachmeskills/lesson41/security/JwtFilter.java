package by.teachmeskills.lesson41.security;

import by.teachmeskills.lesson41.security.service.JwtHelper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.teachmeskills.lesson41.controller.auth.AuthApiController.AUTH_REQUEST_MAPPING;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String JWT_COOKIE_NAME = "JWT";

    private static final String BEARER_PRE = "Bearer ";

    private final JwtHelper jwtHelper;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith(AUTH_REQUEST_MAPPING)) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    String jwt = cookie.getValue();
                    final UserDetails userDetails = jwtHelper.getTokenClaims(jwt);
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
                                    userDetails.getAuthorities())
                    );
                    break;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
