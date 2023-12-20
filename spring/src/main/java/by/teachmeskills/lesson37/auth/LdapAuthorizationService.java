package by.teachmeskills.lesson37.auth;

import by.teachmeskills.lesson37.auth.session.Session;
import by.teachmeskills.lesson37.auth.session.FileServerSessionManager;
import by.teachmeskills.lesson37.auth.session.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LdapAuthorizationService implements AuthorizationService {

    private final FileServerSessionManager sessionManager;

    @Autowired
    public LdapAuthorizationService(FileServerSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean login(String username, String password) {
        Session session = new Session(SessionService.generateSessionId(), username, this);
        sessionManager.addSession(session);
        log.info("{}: Пользователь вошел: {}", this, username);
        return isUserAuthenticated(username);
    }

    @Override
    public void logout(String username) {
        sessionManager.removeSession(sessionManager.getSessionByUsername(username));
        log.info("{}: Пользователь вышел: {}", this, username);
    }

    @Override
    public boolean isUserAuthenticated(String username) {
        boolean isUserAuthenticated = sessionManager.isSessionExistByUsername(username);
        log.info("{}: Статус авторизации пользователя: {}, {}", this, username, isUserAuthenticated);
        return isUserAuthenticated;
    }

}
