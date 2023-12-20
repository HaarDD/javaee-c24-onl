package by.teachmeskills.lesson37.auth;

public interface AuthorizationService {
    boolean login(String username, String password);

    void logout(String username);

    boolean isUserAuthenticated(String username);
}

