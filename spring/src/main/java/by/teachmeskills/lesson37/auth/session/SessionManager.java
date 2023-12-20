package by.teachmeskills.lesson37.auth.session;

public interface SessionManager {

    public void addSession(Session session);

    public void removeSession(Session session);

    public Session getSessionByUsername(String username);

    public boolean isSessionExistByUsername(String username);

    public void logAllSessions();
}
