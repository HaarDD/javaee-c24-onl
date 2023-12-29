package by.teachmeskills.lesson39.dao;

import by.teachmeskills.lesson39.db.DatabaseManager;
import by.teachmeskills.lesson39.dto.AuthorDto;
import by.teachmeskills.lesson39.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private static final String INSERT_BOOK_AUTHOR_SQL = "INSERT INTO book_author (bookId, authorId) VALUES (?, ?)";
    private static final String SELECT_ALL_AUTHORS_SQL = "SELECT * FROM author";
    private static final String SELECT_BOOKS_BY_AUTHOR_SQL = "SELECT book.* FROM book JOIN book_author ON book.id = book_author.bookId WHERE book_author.authorId = ?";
    private static final String DELETE_AUTHOR_SQL = "DELETE FROM author WHERE id = ?";

    private static final String SELECT_AUTHOR_BY_NAME_SQL = "SELECT * FROM author WHERE name = ?";

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
                resultSet.getString("name");
                return resultSet.next();
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

    public static void deleteAuthor(Long authorId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_SQL)) {
            preparedStatement.setLong(1, authorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка удаления автора: ", e);
        }
    }
}
