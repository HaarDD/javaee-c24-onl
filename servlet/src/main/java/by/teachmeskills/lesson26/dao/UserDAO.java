package by.teachmeskills.lesson26.dao;

import by.teachmeskills.lesson26.dto.UserDTO;
import by.teachmeskills.lesson26.services.DatabaseManager;
import by.teachmeskills.lesson26.services.PasswordUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
abstract public class UserDAO {

    private static final String INSERT_USER = "INSERT INTO users (email, firstname, lastname, password_hash, password_salt, role_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE users SET email = ?, firstname = ?, lastname = ?, password_hash = ?, password_salt = ?, role_id = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SELECT_USER_PASSWORD_BY_EMAIL = "SELECT password_hash, password_salt FROM users WHERE email = ?";

    public static void createUser(UserDTO userDTO, String rawPassword) {
        if (emailExists(userDTO.getEmail())) {
            log.info("Пользователь с таким email уже существует: " + userDTO.getEmail());
            return;
        }

        // Генерируем соль
        String salt = PasswordUtils.generateSalt();

        // Хэшируем пароль с использованием соли
        String hashedPassword = PasswordUtils.hashPassword(rawPassword, salt);

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {

            statement.setString(1, userDTO.getEmail());
            statement.setString(2, userDTO.getFirstname());
            statement.setString(3, userDTO.getLastname());
            statement.setString(4, hashedPassword);
            statement.setString(5, salt);
            statement.setLong(6, userDTO.getRoleId());

            statement.executeUpdate();

        } catch (Exception e) {
            log.error("Ошибка создания пользователя: ", e);
        }
    }


    public static List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UserDTO user = mapResultSetToUser(resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            log.error("Ошибка получения списка пользователей: ", e);
        }

        return users;
    }

    public static UserDTO getUserById(Long userId) {
        UserDTO user = new UserDTO();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {

            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения пользоватея по id: ", e);
        }

        return user;
    }

    public static void updateUser(UserDTO userDTO, String password) {
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, userDTO.getEmail());
            statement.setString(2, userDTO.getFirstname());
            statement.setString(3, userDTO.getLastname());
            statement.setString(4, hashedPassword);
            statement.setString(5, salt);
            statement.setLong(6, userDTO.getRoleId());
            statement.setLong(7, userDTO.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUserPassword(String email, String password) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_PASSWORD_BY_EMAIL)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("password_hash");
                    String salt = resultSet.getString("password_salt");
                    String hashedPassword = PasswordUtils.hashPassword(password, salt);
                    return hashedPasswordFromDB.equals(hashedPassword);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static boolean checkUserByEmailAndHashedPassword(String email, String hashedPassword) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_PASSWORD_BY_EMAIL)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("password_hash");
                    return hashedPasswordFromDB.equals(hashedPassword);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static UserDTO getUserByEmail(String email) {
        UserDTO user = null;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean emailExists(String email) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteUser(Long userId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {

            statement.setLong(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static UserDTO mapResultSetToUser(ResultSet resultSet) throws SQLException {
        UserDTO user = new UserDTO();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setPasswordSalt(resultSet.getString("password_salt"));
        user.setRoleId(resultSet.getLong("role_id"));
        return user;
    }
}

