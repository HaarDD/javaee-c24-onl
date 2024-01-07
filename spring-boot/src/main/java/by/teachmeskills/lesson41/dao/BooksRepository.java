package by.teachmeskills.lesson41.dao;

import by.teachmeskills.lesson41.dbservice.DatabaseManager;
import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.dto.BookAuthorDto;
import by.teachmeskills.lesson41.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class BooksRepository {

    private final BooksAuthorsRepository booksAuthorsRepository;
    private static final String INSERT_BOOK_SQL = "INSERT INTO book (name, description, isbn, pages) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM book";
    private static final String SELECT_BOOK_BY_ID_SQL = "SELECT * FROM book WHERE id = ?";
    private static final String EDIT_BOOK_BY_ID_SQL = "UPDATE book SET name=?, description=?, isbn=?, pages=? WHERE id=?";
    private static final String DELETE_BOOK_BY_ID_SQL = "DELETE FROM book WHERE id = ?";

    public List<BookDto> getAllBooks() {
        List<BookDto> books = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                books.add(fillBookData(resultSet, connection));
            }
            log.info("Книги отправлены: {}", Arrays.toString(books.stream().map(BookDto::getId).toArray()));
        } catch (SQLException e) {
            log.error("Ошибка при получении списка книг: ", e);
        }

        return books;
    }

    public Optional<BookDto> addBook(BookDto bookDto) {
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
                        bookDto.setId(bookId);

                        bookDto.getAuthors().stream().map(AuthorDto::getId).forEach(authorId -> {
                            booksAuthorsRepository.addBookAuthorRelationship(connection, new BookAuthorDto(bookId, authorId));
                        });

                    } else {
                        log.error("Ошибка при добавлении книги, не удалось получить сгенерированный ключ ");
                    }
                }
                connection.commit();
                return Optional.of(bookDto);
            } catch (SQLException e) {
                connection.rollback();
                log.error("Ошибка при добавлении книги: ", e);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении соединения: ", e);
        }
        return Optional.empty();
    }

    public Optional<BookDto> editBook(BookDto bookDto) {
        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                for (AuthorDto oldAuthor : booksAuthorsRepository.getAuthorsForBook(connection, bookDto.getId())) {
                    booksAuthorsRepository.removeBookAuthorRelationship(connection, new BookAuthorDto(bookDto.getId(), oldAuthor.getId()));
                }

                for (AuthorDto newAuthor : bookDto.getAuthors()) {
                    booksAuthorsRepository.addBookAuthorRelationship(connection, new BookAuthorDto(bookDto.getId(), newAuthor.getId()));
                }

                try (PreparedStatement updateBookStatement = connection.prepareStatement(EDIT_BOOK_BY_ID_SQL, Statement.RETURN_GENERATED_KEYS)) {
                    updateBookStatement.setString(1, bookDto.getName());
                    updateBookStatement.setString(2, bookDto.getDescription());
                    updateBookStatement.setString(3, bookDto.getIsbn());
                    updateBookStatement.setLong(4, bookDto.getPages());
                    updateBookStatement.setLong(5, bookDto.getId());
                    updateBookStatement.executeUpdate();
                    ResultSet resultSet = updateBookStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        bookDto.setId(resultSet.getLong(1));
                    }
                }

                connection.commit();
                log.info("Книга изменена: {}", bookDto.getId());
                return Optional.of(bookDto);
            } catch (SQLException e) {
                connection.rollback();
                log.error("Ошибка при редактировании книги: ", e);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении соединения: ", e);
        }
        return Optional.empty();
    }

    public Optional<BookDto> deleteBookById(long bookId) {
        Optional<BookDto> optionalDeletedBookDto = getBookById(bookId);
        if (optionalDeletedBookDto.isEmpty()) {
            log.warn("Книги с id \"{}\" не существует", bookId);
            return Optional.empty();
        }
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement deleteBookStatement = connection.prepareStatement(DELETE_BOOK_BY_ID_SQL)) {
            connection.setAutoCommit(false);

            try {
                deleteBookStatement.setLong(1, bookId);
                deleteBookStatement.executeUpdate();
                connection.commit();
                log.info("Книга удалена: {}", bookId);
                return optionalDeletedBookDto;
            } catch (SQLException e) {
                connection.rollback();
                log.error("Ошибка при удалении книги: ", e);
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении соединения: ", e);
        }
        return Optional.empty();
    }

    public Optional<BookDto> getBookById(long bookId) {

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_BY_ID_SQL)) {

            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Optional<BookDto> bookDtoOptional = Optional.of(fillBookData(resultSet, connection));
                log.info("Данные книги отправлены: {}", bookId);
                return bookDtoOptional;
            }

        } catch (SQLException e) {
            log.error("Ошибка при получении книги по ID: ", e);
        }

        return Optional.empty();
    }

    private BookDto fillBookData(ResultSet resultSet, Connection connection) throws SQLException {
        long bookId = resultSet.getLong("id");
        return new BookDto()
                .setId(bookId)
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setIsbn(resultSet.getString("isbn"))
                .setPages(resultSet.getLong("pages"))
                .setAuthors(booksAuthorsRepository.getAuthorsForBook(connection, bookId));
    }
}