package by.teachmeskills.lesson26.services;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    public static String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public static String generateSalt() {
        return BCrypt.gensalt();
    }

    public static boolean checkPassword(String candidate, String hashedPassword) {
        return BCrypt.checkpw(candidate, hashedPassword);
    }
}
