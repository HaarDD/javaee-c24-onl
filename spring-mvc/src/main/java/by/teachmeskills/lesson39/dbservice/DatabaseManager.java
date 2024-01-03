package by.teachmeskills.lesson39.dbservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service
public class DatabaseManager {
    private static final BasicDataSource dataSource = new BasicDataSource();
    private static final String CONFIG_FILE_PATH = "dbconf/database-config.json";
    private static DatabaseConfig databaseConfig;

    private DatabaseManager() {
        configureDataSource();
        createDatabase();
    }

    private static void configureDataSource() {
        try (InputStream inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            ObjectMapper objectMapper = new ObjectMapper();
            databaseConfig = objectMapper.readValue(inputStream, DatabaseConfig.class);

            configureDataSourceFromConfig(databaseConfig);
        } catch (IOException e) {
            log.error("Ошибка чтения файла конфигурации базы данных: ", e);
        }
    }

    private static void configureDataSourceFromConfig(DatabaseConfig config) {
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUser());
        dataSource.setPassword(config.getPassword());
    }

    private static String CREATE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS %s";

    private static String USE_DATABASE_SQL = "USE %s";

    private static String CREATE_TABLE_BOOK_SQL = "CREATE TABLE IF NOT EXISTS book (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, description TEXT DEFAULT NULL, isbn VARCHAR(255) DEFAULT NULL, pages VARCHAR(255) DEFAULT NULL, PRIMARY KEY (id))";

    private static String CREATE_TABLE_AUTHOR_SQL = "CREATE TABLE IF NOT EXISTS author (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100) NOT NULL, PRIMARY KEY (id))";

    private static String CREATE_TABLE_BOOK_AUTHOR_SQL = "CREATE TABLE IF NOT EXISTS book_author (bookid INT NOT NULL, authorid INT NOT NULL, PRIMARY KEY (bookid, authorid), FOREIGN KEY (authorid) REFERENCES author(id) ON DELETE CASCADE, FOREIGN KEY (bookid) REFERENCES book(id) ON DELETE CASCADE)";

    public static void createDatabase() {
        try (Connection connection = getConnection()) {
            createOrUseDatabase(connection, databaseConfig.getDatabaseName());
            createTableBook(connection);
            createTableAuthor(connection);
            createTableBookAuthor(connection);

        } catch (SQLException e) {
            log.error("Ошибка создания базы: ", e);
        }
    }

    private static void createOrUseDatabase(Connection connection, String databaseName) throws SQLException {
        executeUpdate(connection, String.format(CREATE_DATABASE_SQL, databaseName));
        executeUpdate(connection, String.format(USE_DATABASE_SQL, databaseName));
    }

    private static void createTableBook(Connection connection) throws SQLException {
        executeUpdate(connection, CREATE_TABLE_BOOK_SQL);
    }

    private static void createTableAuthor(Connection connection) throws SQLException {
        executeUpdate(connection, CREATE_TABLE_AUTHOR_SQL);
    }

    private static void createTableBookAuthor(Connection connection) throws SQLException {
        executeUpdate(connection, CREATE_TABLE_BOOK_AUTHOR_SQL);
    }

    private static void executeUpdate(Connection connection, String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
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
