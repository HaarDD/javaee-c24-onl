package by.teachmeskills.lesson37;

import by.teachmeskills.lesson37.auth.session.FileServerSessionManager;
import by.teachmeskills.lesson37.config.FileServerConfig;
import by.teachmeskills.lesson37.auth.AuthorizationService;
import by.teachmeskills.lesson37.file.SimpleFile;
import by.teachmeskills.lesson37.file.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class SimpleFileServerApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(FileServerConfig.class);

        AuthorizationService localAuthService = context.getBean("localAuthorizationService", AuthorizationService.class);
        AuthorizationService ldapAuthService = context.getBean("ldapAuthorizationService", AuthorizationService.class);

        // Имитация логина пользователей
        localAuthService.login("user1", "password1");
        ldapAuthService.login("user2", "password2");

        // Создание FileManager
        FileManager fileManager = context.getBean(FileManager.class);

        // Добавление файлов
        SimpleFile file1 = new SimpleFile("file1.txt", "/path/to/file1", "File content 1".getBytes(), "txt");
        SimpleFile file2 = new SimpleFile("file2.txt", "/path/to/file2", "File content 2".getBytes(), "txt");
        fileManager.addFile(file1);
        fileManager.addFile(file2);

        // Имитация получения файла авторизованными и неавторизованными пользователями
        fileManager.getFile("file1.txt", "user1");
        fileManager.getFile("file2.txt", "user3");

        // Вывод всех сессий
        FileServerSessionManager sessionManager = context.getBean(FileServerSessionManager.class);
        sessionManager.logAllSessions();

        //Получение бина, определенного в конфигурации
        log.info("Имя приложения: {}", context.getBean("appName"));

    }

}
