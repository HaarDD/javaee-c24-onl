package by.teachmeskills.lesson39.dao;

import by.teachmeskills.lesson39.dto.AuthorDto;
import by.teachmeskills.lesson39.dto.BookAuthorDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BookAuthorDao {
    private static final String INSERT_BOOK_AUTHOR_SQL = "INSERT INTO book_author (bookId, authorId) VALUES (?, ?)";
    private static final String SELECT_AUTHORS_FOR_BOOK = "SELECT author.* FROM author JOIN book_author ON author.id = book_author.authorId WHERE book_author.bookId = ?";
    private static final String DELETE_BOOK_AUTHOR_RELATIONSHIP_SQL = "DELETE FROM book_author WHERE bookId = ? AND authorId = ?";

    public static void addBookAuthorRelationship(Connection connection, BookAuthorDto bookAuthorDto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_AUTHOR_SQL)) {
            preparedStatement.setLong(1, bookAuthorDto.getBookId());
            preparedStatement.setLong(2, bookAuthorDto.getAuthorId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка добавления связи между книгой и автором: ", e);
        }
    }

    public static void removeBookAuthorRelationship(Connection connection, BookAuthorDto bookAuthorDto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_AUTHOR_RELATIONSHIP_SQL)) {
            preparedStatement.setLong(1, bookAuthorDto.getBookId());
            preparedStatement.setLong(2, bookAuthorDto.getAuthorId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка удаления связи между книгой и автором: ", e);
        }
    }

    public static List<AuthorDto> getAuthorsForBook(Connection connection, long bookId) {
        List<AuthorDto> authors = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_AUTHORS_FOR_BOOK)) {
            statement.setLong(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    authors.add(new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name"))
                    );
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении списка авторов для книги: ", e);
        }

        return authors;
    }

}
