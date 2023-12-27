package by.teachmeskills.lesson37.auth.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component("defaultSessionManager")
@Slf4j
public class FileServerSessionManager implements SessionManager {
    private final Set<Session> sessionSet = new HashSet<Session>();

    @Override
    public void addSession(Session session) {
        sessionSet.add(session);
    }

    @Override
    public void removeSession(Session session) {
        sessionSet.remove(session);
    }

    @Override
    public Session getSessionByUsername(String username) {
        return sessionSet.stream()
                .filter(session -> session.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isSessionExistByUsername(String username) {
        return sessionSet.stream()
                .anyMatch(session -> session.getUsername().equals(username));
    }

    @Override
    public void logAllSessions() {
        log.info("Все сессии");
        sessionSet.forEach(session -> log.info("Сессия {}", session));
    }
}