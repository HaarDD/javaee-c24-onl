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

    private static final String RESOURCE_LOCATION = "src/main/resources/";
    private static final String DEFAULT_IMAGE_PATH = "/images/Solar_System.jpg";
    private static final Map<String, String> IMAGE_PATH_MAP = Map.of(
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

            String imagePath = RESOURCE_LOCATION + IMAGE_PATH_MAP.getOrDefault(name, DEFAULT_IMAGE_PATH);

            byte[] imageBytes = ResourcesUtils.extractBytes(imagePath);

            exchange.getResponseHeaders().set("Content-Type", "image/jpeg");
            exchange.sendResponseHeaders(200, imageBytes.length);

            responseBody.write(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
