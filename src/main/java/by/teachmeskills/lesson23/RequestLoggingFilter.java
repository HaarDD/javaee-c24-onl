package by.teachmeskills.lesson23;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter("/*")
public class RequestLoggingFilter implements Filter {

    private static final SimpleDateFormat LOG_FILE_DATE_FORMAT = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

    private static final String LOG_FILE_NAME = "log_" + LOG_FILE_DATE_FORMAT.format(new Date()) + ".txt";

    private static final String LOG_PATH = "/storage/logs";

    private File logFile;
    private FileWriter logFileWriter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        File logsDirectory = new File(filterConfig.getServletContext().getRealPath("/storage/logs"));

        if (!logsDirectory.exists()) {
            boolean created = logsDirectory.mkdirs();
            if (created) {
                System.out.println("Папка logs создана: " + logsDirectory.getAbsolutePath());
            } else {
                System.out.println("Не удалось создать папку logs (возможно, она уже существует).");
            }
        }

        logFile = new File(filterConfig.getServletContext().getRealPath(LOG_PATH) + File.separator + LOG_FILE_NAME);

        try {
            createNewLogFile();
            logFileWriter = new FileWriter(logFile, true);
        } catch (IOException e) {
            System.out.println("Файл логов или его writer не создан!");
        }

        Filter.super.init(filterConfig);
    }

    private void createNewLogFile() throws IOException {
        if (logFile.createNewFile()) {
            System.out.println("Файл логов создан: " + logFile.getAbsoluteFile());
        } else {
            System.out.println("Файл логов уже существует: " + logFile.getAbsoluteFile());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        logFileWriter.write("Time: " + new Date() + '\n');

        if (session != null) {
            String sessionId = session.getId();
            logFileWriter.write("Session ID: " + sessionId + '\n');
        }

        logFileWriter.write("Request URL: " + httpRequest.getRequestURL() + '\n');
        logFileWriter.write("Request Method: " + httpRequest.getMethod() + '\n');

        chain.doFilter(request, response);

        logFileWriter.write("Request processing completed." + "\n\n");
    }

}
