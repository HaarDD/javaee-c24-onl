package lesson32.front_controller.service;

import lombok.Getter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Getter
public class PropertiesService {
    public static Properties getLoadedProperties(String propertiesFilename, ClassLoader classLoader) throws ServletException {
        Properties properties = new Properties();
        try (InputStream input = classLoader.getResourceAsStream(propertiesFilename)) {
            if (Objects.isNull(input)) {
                throw new IOException("Файл конфигурации не найден: " + propertiesFilename);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new ServletException("Невозможно загрузить файл конфигурации: " + propertiesFilename, e);
        }
        return properties;
    }

}
