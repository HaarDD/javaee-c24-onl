package by.teachmeskills.lesson39.dao;

import by.teachmeskills.lesson39.db.DatabaseManager;
import by.teachmeskills.lesson39.dto.AuthorDto;
import by.teachmeskills.lesson39.dto.BookAuthorDto;
import by.teachmeskills.lesson39.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BookDao {
    private static final String INSERT_BOOK_SQL = "INSERT INTO book (name, description, isbn, pages) VALUES (?, ?, ?, ?)";
    private static final String INSERT_BOOK_AUTHOR_SQL = "INSERT INTO book_author (bookId, authorId) VALUES (?, ?)";
    private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM book";

    public static List<BookDto> getAllBooks() {
        List<BookDto> books = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                BookDto book = new BookDto();
                long bookId = resultSet.getLong("id");
                book.setId(bookId);
                book.setName(resultSet.getString("name"));
                book.setDescription(resultSet.getString("description"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPages(resultSet.getLong("pages"));


                List<AuthorDto> authors = BookAuthorDao.getAuthorsForBook(connection, bookId);
                book.setAuthors(authors);

                books.add(book);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении списка книг: ", e);
        }

        return books;
    }

    public static void addAuthorToAuthorBook(BookDto bookDto, AuthorDto authorDto) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_AUTHOR_SQL)) {
            preparedStatement.setLong(1, bookDto.getId());
            preparedStatement.setLong(2, authorDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка добавления книги к автору: ", e);
        }
    }

    public static void addBook(BookDto bookDto, List<Long> authorIds) {
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement insertBookStatement = connection.prepareStatement(INSERT_BOOK_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertBookStatement.setString(1, bookDto.getName());
                insertBookStatement.setString(2, bookDto.getDescription());
                insertBookStatement.setString(3, bookDto.getIsbn());
                insertBookStatement.setLong(4, bookDto.getPages());
                insertBookStatement.executeUpdate();

                try (ResultSet generatedKeys = insertBookStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long bookId = generatedKeys.getLong(1);

                        for (Long authorId : authorIds) {
                            BookAuthorDao.addBookAuthorRelationship(connection, new BookAuthorDto(bookId, authorId));
                        }

                    } else {
                        throw new SQLException("Ошибка при добавлении книги, не удалось получить сгенерированный ключ.");
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Ошибка при добавлении книги: ", e);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении соединения: ", e);
        }
    }

    public static void addBook(BookDto bookDto) {
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement insertBookStatement = connection.prepareStatement(INSERT_BOOK_SQL)) {
                insertBookStatement.setString(1, bookDto.getName());
                insertBookStatement.setString(2, bookDto.getDescription());
                insertBookStatement.setString(3, bookDto.getIsbn());
                insertBookStatement.setLong(4, bookDto.getPages());
                insertBookStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Ошибка при добавлении книги: ", e);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении соединения: ", e);
        }
    }

}
