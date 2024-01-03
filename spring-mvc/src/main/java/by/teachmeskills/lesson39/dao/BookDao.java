package by.teachmeskills.lesson39.dao;

import by.teachmeskills.lesson39.dbservice.DatabaseManager;
import by.teachmeskills.lesson39.dto.AuthorDto;
import by.teachmeskills.lesson39.dto.BookAuthorDto;
import by.teachmeskills.lesson39.dto.BookDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class BookDao {
    private static final String INSERT_BOOK_SQL = "INSERT INTO book (name, description, isbn, pages) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM book";
    private static final String SELECT_BOOK_BY_ID_SQL = "SELECT * FROM book WHERE id = ?";
    private static final String EDIT_BOOK_BY_ID_SQL = "UPDATE book SET name=?, description=?, isbn=?, pages=? WHERE id=?";
    private static final String DELETE_BOOK_BY_ID_SQL = "DELETE FROM book WHERE id = ?";

    public static List<BookDto> getAllBooks() {
        List<BookDto> books = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                BookDto book = new BookDto();
                long bookId = resultSet.getLong("id");
                book.setId(bookId);

                fillBookData(bookId, book, connection, resultSet);

                books.add(book);
            }
            log.info("Книги отправлены: {}", Arrays.toString(books.stream().map(BookDto::getId).toArray()));
        } catch (SQLException e) {
            log.error("Ошибка при получении списка книг: ", e);
        }

        return books;
    }

    public static void addBook(BookDto bookDto) {
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

                        bookDto.getAuthors().stream().map(AuthorDto::getId).forEach(authorId -> {
                            BookAuthorDao.addBookAuthorRelationship(connection, new BookAuthorDto(bookId, authorId));
                        });

                    } else {
                        log.error("Ошибка при добавлении книги, не удалось получить сгенерированный ключ ");
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

    public static void editBook(BookDto bookDto) {
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                for (AuthorDto oldAuthor : BookAuthorDao.getAuthorsForBook(connection, bookDto.getId())) {
                    BookAuthorDao.removeBookAuthorRelationship(connection, new BookAuthorDto(bookDto.getId(), oldAuthor.getId()));
                }

                for (AuthorDto newAuthor : bookDto.getAuthors()) {
                    BookAuthorDao.addBookAuthorRelationship(connection, new BookAuthorDto(bookDto.getId(), newAuthor.getId()));
                }

                try (PreparedStatement updateBookStatement = connection.prepareStatement(EDIT_BOOK_BY_ID_SQL)) {
                    updateBookStatement.setString(1, bookDto.getName());
                    updateBookStatement.setString(2, bookDto.getDescription());
                    updateBookStatement.setString(3, bookDto.getIsbn());
                    updateBookStatement.setLong(4, bookDto.getPages());
                    updateBookStatement.setLong(5, bookDto.getId());
                    updateBookStatement.executeUpdate();
                }

                connection.commit();
                log.info("Книга изменена: {}", bookDto.getId());
            } catch (SQLException e) {
                connection.rollback();
                log.error("Ошибка при редактировании книги: ", e);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении соединения: ", e);
        }
    }

    public static void deleteBookById(long bookId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement deleteBookStatement = connection.prepareStatement(DELETE_BOOK_BY_ID_SQL)) {
            connection.setAutoCommit(false);

            try {
                deleteBookStatement.setLong(1, bookId);
                deleteBookStatement.executeUpdate();
                connection.commit();
                log.info("Книга удалена: {}", bookId);
            } catch (SQLException e) {
                connection.rollback();
                log.error("Ошибка при удалении книги: ", e);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении соединения: ", e);
        }
    }

    public static BookDto getBookById(long bookId) {
        BookDto book = null;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_BY_ID_SQL)) {

            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new BookDto();
                book.setId(resultSet.getLong("id"));
                fillBookData(bookId, book, connection, resultSet);
            }
            log.info("Книга получена: {}", bookId);

        } catch (SQLException e) {
            log.error("Ошибка при получении книги по ID: ", e);
        }

        return book;
    }

    private static void fillBookData(long bookId, BookDto book, Connection connection, ResultSet resultSet) throws SQLException {
        book.setName(resultSet.getString("name"));
        book.setDescription(resultSet.getString("description"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPages(resultSet.getLong("pages"));

        List<AuthorDto> authors = BookAuthorDao.getAuthorsForBook(connection, bookId);
        book.setAuthors(authors);
    }
}