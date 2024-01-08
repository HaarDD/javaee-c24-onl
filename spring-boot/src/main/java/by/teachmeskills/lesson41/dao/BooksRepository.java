package by.teachmeskills.lesson41.dao;

import by.teachmeskills.lesson41.dbservice.DatabaseManager;
import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.dto.BookAuthorDto;
import by.teachmeskills.lesson41.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class BooksRepository {

    private final BooksAuthorsRepository booksAuthorsRepository;
    private static final String INSERT_BOOK_SQL = "INSERT INTO book (name, description, isbn, pages) VALUES (:name, :description, :isbn, :pages)";
    private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM book";
    private static final String SELECT_BOOK_BY_ID_SQL = "SELECT * FROM book WHERE id = :id";
    private static final String EDIT_BOOK_BY_ID_SQL = "UPDATE book SET name=:name, description=:description, isbn=:isbn, pages=:pages WHERE id=:id";
    private static final String DELETE_BOOK_BY_ID_SQL = "DELETE FROM book WHERE id = :id";

    private static final String SELECT_FILTERED_BOOKS_SQL =
            "SELECT book.* " +
                    "FROM book " +
                    "LEFT JOIN book_author ON book.id = book_author.bookId " +
                    "LEFT JOIN author ON book_author.authorId = author.id " +
                    "WHERE (:searchText IS NULL OR " +
                    "(:searchType = 'name' AND  LOWER(book.name) LIKE LOWER(CONCAT('%', :searchText, '%'))) OR " +
                    "(:searchType = 'description' AND LOWER(book.description) LIKE LOWER(CONCAT('%', :searchText, '%'))) OR " +
                    "(:searchType = 'isbn' AND LOWER(book.isbn) LIKE LOWER(CONCAT('%', :searchText, '%')))) " +
                    "AND (:authorSelectIsNull IS NULL OR author.id IN (:authorSelect)) " +
                    "AND (:pagesFrom IS NULL OR book.pages > :pagesFrom) " +
                    "AND (:pagesTo IS NULL OR book.pages < :pagesTo)";

    public List<BookDto> getFilteredBooks(String searchText, String searchType, List<Long> authorSelect, Long pagesFrom, Long pagesTo) {
        List<BookDto> filteredBooks = new ArrayList<>();

        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("searchText", searchText, Types.VARCHAR, "VARCHAR");
            parameters.addValue("searchType", searchType, Types.VARCHAR, "VARCHAR");
            parameters.addValue("authorSelectIsNull", authorSelect!=null ? "NOT_NULL" : null);
            parameters.addValue("authorSelect", authorSelect);
            parameters.addValue("pagesFrom", pagesFrom, Types.INTEGER, "INT");
            parameters.addValue("pagesTo", pagesTo, Types.INTEGER, "INT");

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());

            filteredBooks = jdbcTemplate.query(SELECT_FILTERED_BOOKS_SQL, parameters, (resultSet, rowNum) ->
                    new BookDto()
                            .setId(resultSet.getLong("id"))
                            .setName(resultSet.getString("name"))
                            .setIsbn(resultSet.getString("isbn"))
                            .setPages(resultSet.getLong("pages"))
                            .setDescription(resultSet.getString("description"))
            );

            filteredBooks.forEach(book -> {
                try {
                    book.setAuthors(booksAuthorsRepository.getAuthorsForBook(book.getId()));
                } catch (Exception e) {
                    log.error("Не удалось проставить авторов книге: {}", book);
                }
            });

            return filteredBooks;

        } catch (Exception e) {
            log.error("Ошибка при получении отфильтрованного списка книг: ", e);
        }

        return filteredBooks;
    }

    public List<BookDto> getAllBooks() {
        List<BookDto> books = new ArrayList<>();

        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            books = jdbcTemplate.query(SELECT_ALL_BOOKS_SQL, (resultSet, rowNum) ->
                    fillBookData(resultSet)
            );
            log.info("Книги отправлены: {}", books.stream().map(BookDto::getId).toArray());
        } catch (Exception e) {
            log.error("Ошибка при получении списка книг: ", e);
        }

        return books;
    }

    public Optional<BookDto> addBook(BookDto bookDto) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", bookDto.getName());
            parameters.addValue("description", bookDto.getDescription());
            parameters.addValue("isbn", bookDto.getIsbn());
            parameters.addValue("pages", bookDto.getPages());

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());

            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                    .withTableName("book")
                    .usingGeneratedKeyColumns("id");

            Number bookId = simpleJdbcInsert.executeAndReturnKey(parameters);
            bookDto.setId(bookId.longValue());

            bookDto.getAuthors().stream().map(AuthorDto::getId).forEach(authorId -> {
                booksAuthorsRepository.addBookAuthorRelationship(new BookAuthorDto(bookId.longValue(), authorId));
            });

            log.info("Книга добавлена: {}", bookDto.getId());
            return Optional.of(bookDto);
        } catch (DataAccessException e) {
            log.error("Ошибка при добавлении книги: ", e);
        }

        return Optional.empty();
    }

    public Optional<BookDto> editBook(BookDto bookDto) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", bookDto.getName());
            parameters.addValue("description", bookDto.getDescription());
            parameters.addValue("isbn", bookDto.getIsbn());
            parameters.addValue("pages", bookDto.getPages());
            parameters.addValue("id", bookDto.getId());

            jdbcTemplate.update(EDIT_BOOK_BY_ID_SQL, parameters);

            // Добавление старых связей в Книги-Авторы
            for (AuthorDto oldAuthor : booksAuthorsRepository.getAuthorsForBook(bookDto.getId())) {
                booksAuthorsRepository.removeBookAuthorRelationship(new BookAuthorDto(bookDto.getId(), oldAuthor.getId()));
            }

            // Добавление новых связей в Книги-Авторы
            for (AuthorDto newAuthor : bookDto.getAuthors()) {
                booksAuthorsRepository.addBookAuthorRelationship(new BookAuthorDto(bookDto.getId(), newAuthor.getId()));
            }

            log.info("Книга изменена: {}", bookDto.getId());
            return Optional.of(bookDto);
        } catch (DataAccessException e) {
            log.error("Ошибка при редактировании книги: ", e);
        }

        return Optional.empty();
    }

    public Optional<BookDto> deleteBookById(long bookId) {
        Optional<BookDto> optionalDeletedBookDto = getBookById(bookId);
        if (optionalDeletedBookDto.isEmpty()) {
            log.warn("Книги с id \"{}\" не существует", bookId);
            return Optional.empty();
        }

        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", bookId);

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            jdbcTemplate.update(DELETE_BOOK_BY_ID_SQL, parameters);

            log.info("Книга удалена: {}", bookId);
            return optionalDeletedBookDto;
        } catch (DataAccessException e) {
            log.error("Ошибка при удалении книги: ", e);
        }

        return Optional.empty();
    }

    public Optional<BookDto> getBookById(long bookId) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", bookId);

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            List<BookDto> books = jdbcTemplate.query(SELECT_BOOK_BY_ID_SQL, parameters, (resultSet, rowNum) ->
                    fillBookData(resultSet)
            );

            if (!books.isEmpty()) {
                BookDto bookDto = books.get(0);
                bookDto.setAuthors(booksAuthorsRepository.getAuthorsForBook(bookDto.getId()));
                log.info("Данные книги отправлены: {}", bookId);
                return Optional.of(bookDto);
            }
        } catch (DataAccessException e) {
            log.error("Ошибка при получении книги по ID: ", e);
        }

        return Optional.empty();
    }

    private BookDto fillBookData(ResultSet resultSet) throws SQLException {
        long bookId = resultSet.getLong("id");
        return new BookDto()
                .setId(bookId)
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setIsbn(resultSet.getString("isbn"))
                .setPages(resultSet.getLong("pages"))
                .setAuthors(booksAuthorsRepository.getAuthorsForBook(bookId));
    }
}
