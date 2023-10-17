package by.teachmeskills.lesson21.handlers;

import by.teachmeskills.lesson21.util.HttpUtils;
import by.teachmeskills.lesson21.util.ResourcesUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class SolarSystemHandler implements HttpHandler {

    private static final String RESOURCE_LOCATION = "src/main/resources/pages/solar_system";
    private static final String DEFAULT_HTML_PAGE_PATH = "/solar_system.html";
    private static final Map<String, String> IMAGE_PATH_MAP = Map.of(
            "solar_system","/images/Solar_System.jpg",
            "sun", "/images/Sun.jpg",
            "earth", "/images/Earth.jpg",
            "moon", "/images/Moon.jpg"
    );

    @Override
    public void handle(HttpExchange exchange) {
        URI requestURI = exchange.getRequestURI();
        String queryParam = requestURI.getQuery();
        Map<String, String> resultMap = queryParam == null || queryParam.isEmpty()
                ? Collections.emptyMap()
                : HttpUtils.parseUriQueryParams(queryParam);

        try (OutputStream responseBody = exchange.getResponseBody()) {
            String name = Objects.requireNonNullElse(resultMap.get("heavenly_body"), "default");

            if (name.equals("default")){
                String htmlPath = RESOURCE_LOCATION + DEFAULT_HTML_PAGE_PATH;

                byte[] htmlBytes = ResourcesUtils.extractBytes(htmlPath);

                exchange.getResponseHeaders().set("Content-Type","text/html");
                exchange.sendResponseHeaders(200,htmlBytes.length);
                responseBody.write(htmlBytes);

            } else {
                String imagePath = RESOURCE_LOCATION + IMAGE_PATH_MAP.get(name);

                byte[] imageBytes = ResourcesUtils.extractBytes(imagePath);

                exchange.getResponseHeaders().set("Content-Type", "image/jpeg");
                exchange.sendResponseHeaders(200, imageBytes.length);
                responseBody.write(imageBytes);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
