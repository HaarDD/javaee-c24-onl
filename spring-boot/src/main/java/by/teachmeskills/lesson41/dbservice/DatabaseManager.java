package by.teachmeskills.lesson41.dbservice;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service
public class DatabaseManager {
    @Getter
    private static final MysqlDataSource dataSource = new MysqlDataSource();

    @Autowired
    public DatabaseManager(DataSourceProperties dataSourceProperties) {
        dataSource.setUrl(dataSourceProperties.getUrl()/* + dataSourceProperties.getName()*/);
        dataSource.setDatabaseName(dataSourceProperties.getName());
        dataSource.setUser(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        createDatabase(dataSourceProperties);
        dataSource.setUrl(dataSourceProperties.getUrl() + dataSourceProperties.getName());
    }

    private static String CREATE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS %s";

    private static String USE_DATABASE_SQL = "USE %s";

    private static String CREATE_TABLE_BOOK_SQL = "CREATE TABLE IF NOT EXISTS book (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, description TEXT DEFAULT NULL, isbn VARCHAR(255) DEFAULT NULL, pages VARCHAR(255) DEFAULT NULL, PRIMARY KEY (id))";

    private static String CREATE_TABLE_AUTHOR_SQL = "CREATE TABLE IF NOT EXISTS author (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100) NOT NULL, PRIMARY KEY (id))";

    private static String CREATE_TABLE_BOOK_AUTHOR_SQL = "CREATE TABLE IF NOT EXISTS book_author (bookid INT NOT NULL, authorid INT NOT NULL, PRIMARY KEY (bookid, authorid), FOREIGN KEY (authorid) REFERENCES author(id) ON DELETE CASCADE, FOREIGN KEY (bookid) REFERENCES book(id) ON DELETE CASCADE)";

    private void createDatabase(DataSourceProperties dataSourceProperties) {
        try (Connection connection = getConnection()) {
            createDatabase(connection, dataSourceProperties.getName());
            useDatabase(connection, dataSourceProperties.getName());
            createTableBook(connection);
            createTableAuthor(connection);
            createTableBookAuthor(connection);

        } catch (SQLException e) {
            log.error("Ошибка создания базы: ", e);
        }
    }

    private void createDatabase(Connection connection, String databaseName) throws SQLException {
        executeUpdate(connection, String.format(CREATE_DATABASE_SQL, databaseName));
    }

    private void useDatabase(Connection connection, String databaseName) throws SQLException {
        executeUpdate(connection, String.format(USE_DATABASE_SQL, databaseName));
    }

    private void createTableBook(Connection connection) throws SQLException {
        executeUpdate(connection, CREATE_TABLE_BOOK_SQL);
    }

    private void createTableAuthor(Connection connection) throws SQLException {
        executeUpdate(connection, CREATE_TABLE_AUTHOR_SQL);
    }

    private void createTableBookAuthor(Connection connection) throws SQLException {
        executeUpdate(connection, CREATE_TABLE_BOOK_AUTHOR_SQL);
    }

    private void executeUpdate(Connection connection, String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
