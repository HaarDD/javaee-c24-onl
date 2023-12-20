package by.teachmeskills.lesson37.file;

import by.teachmeskills.lesson37.auth.session.Session;
import by.teachmeskills.lesson37.auth.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FileManager {

    private final List<SimpleFile> fileStorage = new ArrayList<>();
    private final SessionManager sessionManager;

    @Autowired
    public FileManager(@Qualifier("defaultSessionManager") SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void addFile(SimpleFile file) {
        fileStorage.add(file);
    }

    public SimpleFile getFile(String fileName, String username) {
        if (sessionManager.isSessionExistByUsername(username) && sessionManager.getSessionByUsername(username).getAuthorizationService().isUserAuthenticated(username)) {
            Session session = sessionManager.getSessionByUsername(username);
            if (session != null) {
                log.info("Пользователь получил файл: {}, {}", fileName, username);
                return getFileFromStorage(fileName);
            }
        } else {
            log.info("Неавторизованный пользователь попытался получить файл: {}, {}", fileName, username);
        }
        return null;
    }

    private SimpleFile getFileFromStorage(String fileName) {
        return fileStorage.stream()
                .filter(file -> file.getFileName().equals(fileName))
                .findFirst()
                .orElseGet(() -> {
                    log.warn("Файл не найден: {}", fileName);
                    return null;
                });
    }
}
