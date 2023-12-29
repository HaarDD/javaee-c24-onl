package by.teachmeskills.lesson39.db;

import org.apache.commons.dbcp2.BasicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "96G&yw#Q";
    private static final String DATABASE_NAME = "lesson37_bookstore";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
    }


    public DatabaseManager() {
        createDatabase();
    }

    public static void createDatabase() {
        try (Connection connection = getConnection()) {
            try (PreparedStatement createDbStatement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
                 PreparedStatement useStatement = connection.prepareStatement("USE " + DATABASE_NAME)) {

                createDbStatement.executeUpdate();
                useStatement.executeUpdate();
            }

            createTableBook(connection);
            createTableAuthor(connection);
            createTableBookAuthor(connection);

        } catch (SQLException e) {
            log.error("Ошибка создания базы: ", e);
        }
    }

    private static void createTableBook(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS book (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, authorid INT DEFAULT NULL, description VARCHAR(255) DEFAULT NULL, isbn VARCHAR(255) DEFAULT NULL, pages VARCHAR(255) DEFAULT NULL, filepath VARCHAR(255) NOT NULL, PRIMARY KEY (id));")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка создания таблицы: ", e);
        }
    }

    private static void createTableAuthor(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS author (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100) NOT NULL, PRIMARY KEY (id));")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка создания таблицы: ", e);
        }
    }

    private static void createTableBookAuthor(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS book_author (bookid INT NOT NULL, authorid INT NOT NULL, PRIMARY KEY (bookid, authorid), FOREIGN KEY (authorid) REFERENCES author(id) ON DELETE CASCADE, FOREIGN KEY (bookid) REFERENCES book(id) ON DELETE CASCADE);")) {
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