package by.teachmeskills.lesson22;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TimeZone;

@WebServlet(urlPatterns = {"/minsk", "/washington", "/beijing"})
public class TimeServlet extends HttpServlet {
    Map<String, ZoneId> cityTimeZoneMap;

    {
        cityTimeZoneMap = Map.of(
                "minsk", TimeZone.getTimeZone("Europe/Minsk").toZoneId(),
                "washington", TimeZone.getTimeZone("America/New_York").toZoneId(),
                "beijing", TimeZone.getTimeZone("Asia/Shanghai").toZoneId()

        );
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        PrintWriter writer = response.getWriter();
        getTimeStrByCityPath(cityTimeZoneMap, requestURI, writer);
    }

    private void getTimeStrByCityPath(Map<String, ZoneId> cityTimeZoneMap, String requestURI, PrintWriter writer) {
        cityTimeZoneMap.forEach((k, v) -> {
            if (requestURI.contains(k)) {
                String time = ZonedDateTime.now(v).toLocalDateTime().format(DateTimeFormatter.ISO_DATE)
                        + " " + ZonedDateTime.now(v).toLocalDateTime().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);
                writer.println("It is now " + time + " in the " + k + ".");
            }
        });
    }

}