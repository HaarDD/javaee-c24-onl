package by.teachmeskills.lesson39.dao;

import by.teachmeskills.lesson39.dbservice.DatabaseManager;
import by.teachmeskills.lesson39.dto.AuthorDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthorDao {
    private static final String INSERT_AUTHOR_SQL = "INSERT INTO author (name) VALUES (?)";
    private static final String SELECT_ALL_AUTHORS_SQL = "SELECT * FROM author";
    private static final String DELETE_AUTHOR_SQL = "DELETE FROM author WHERE id = ?";

    private static final String SELECT_AUTHOR_BY_NAME_SQL = "SELECT * FROM author WHERE name = ?";

    private static final String SELECT_AUTHOR_BY_ID_SQL = "SELECT * FROM author WHERE id = ?";
    private static final String UPDATE_AUTHOR_SQL = "UPDATE author SET name = ? WHERE id = ?";

    public static Long addAuthor(String name) {
        if (authorExists(name)) {
            log.warn("Автор с именем {} уже существует", name);
            return null;
        }

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.error("Ошибка добавления автора: ", e);
        }
        return null;
    }

    private static boolean authorExists(String name) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME_SQL)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resultSet.getString("name");
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка проверки существования автора: ", e);
        }
        return false;
    }

    public static List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authors = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTHORS_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AuthorDto author = new AuthorDto(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                );
                authors.add(author);
            }
        } catch (SQLException e) {
            log.error("Ошибка получения списка авторов: ", e);
        }
        return authors;
    }

    public static void deleteAuthorById(Long authorId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_SQL)) {
            preparedStatement.setLong(1, authorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка удаления автора: ", e);
        }
    }

    public static AuthorDto getAuthorById(Long authorId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID_SQL)) {
            preparedStatement.setLong(1, authorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка получения автора по идентификатору: ", e);
        }
        return null;
    }

    public static void editAuthor(AuthorDto authorDto) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_SQL)) {
            preparedStatement.setString(1, authorDto.getName());
            preparedStatement.setLong(2, authorDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка редактирования автора: ", e);
        }
    }
}
