package by.teachmeskills.lesson26.services;

import org.apache.commons.dbcp2.BasicDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "96G&yw#Q";
    private static final String DATABASE_NAME = "lesson35";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
    }

    public static void createDatabase() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
             PreparedStatement useStatement = connection.prepareStatement("USE " + DATABASE_NAME)) {

            statement.executeUpdate();
            useStatement.executeUpdate();

            createTableRoles(connection);
            createTableUsers(connection);
            createTableRepairRequest(connection);
            createTableRepairTypes(connection);
            createTableRepairRequestRepairTypes(connection);

        } catch (SQLException e) {
            log.error("Ошибка создания базы: ", e);
        }
    }

    private static void createTableRoles(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS roles (id INT PRIMARY KEY AUTO_INCREMENT, role_name VARCHAR(50) DEFAULT NULL) ENGINE = INNODB, CHARACTER SET utf8mb4, COLLATE utf8mb4_0900_ai_ci;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка создания таблицы: ", e);
        }
    }

    private static void createTableUsers(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, email VARCHAR(50) NOT NULL, firstname VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL, password_hash CHAR(60) NOT NULL, password_salt CHAR(29) NOT NULL, role_id INT NOT NULL, CONSTRAINT FK_users_roles_id FOREIGN KEY (role_id) REFERENCES roles (id)) ENGINE = INNODB, CHARACTER SET utf8mb4, COLLATE utf8mb4_0900_ai_ci;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка создания таблицы: ", e);
        }
    }

    private static void createTableRepairRequest(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS repair_request (id INT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, session_id VARCHAR(50) NOT NULL, address VARCHAR(255) DEFAULT NULL, PRIMARY KEY (id), CONSTRAINT FK_repair_request_users_id FOREIGN KEY (user_id) REFERENCES users (id)) ENGINE = INNODB, CHARACTER SET utf8mb4, COLLATE utf8mb4_0900_ai_ci;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка создания таблицы: ", e);
        }
    }

    private static void createTableRepairTypes(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS repair_types (id INT NOT NULL AUTO_INCREMENT, code VARCHAR(50) NOT NULL, name VARCHAR(255) NOT NULL, PRIMARY KEY (id)) ENGINE = INNODB, CHARACTER SET utf8mb4, COLLATE utf8mb4_0900_ai_ci;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка создания таблицы: ", e);
        }
    }

    private static void createTableRepairRequestRepairTypes(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS repair_request_repair_types (repair_request_id INT NOT NULL, repair_type_id INT NOT NULL, PRIMARY KEY (repair_request_id, repair_type_id), CONSTRAINT FK_repair_request_repair_types_repair_request_id FOREIGN KEY (repair_request_id) REFERENCES repair_request (id), CONSTRAINT FK_repair_request_repair_types_repair_types_id FOREIGN KEY (repair_type_id) REFERENCES repair_types (id)) ENGINE = INNODB, CHARACTER SET utf8mb4, COLLATE utf8mb4_0900_ai_ci;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка создания таблицы: ", e);
        }
    }

    public static void closeDataSource() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            log.error("Ошибка закрытия соединения", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
