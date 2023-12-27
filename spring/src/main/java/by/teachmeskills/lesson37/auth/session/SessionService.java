package by.teachmeskills.lesson37.auth.session;

import java.util.UUID;

public class SessionService {
    public static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
