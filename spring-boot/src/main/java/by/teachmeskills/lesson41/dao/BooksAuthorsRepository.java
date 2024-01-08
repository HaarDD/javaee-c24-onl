package by.teachmeskills.lesson41.dao;

import by.teachmeskills.lesson41.dbservice.DatabaseManager;
import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.dto.BookAuthorDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository
public class BooksAuthorsRepository {

    private static final String INSERT_BOOK_AUTHOR_SQL = "INSERT INTO book_author (bookId, authorId) VALUES (:bookId, :authorId)";
    private static final String SELECT_AUTHORS_FOR_BOOK = "SELECT author.* FROM author JOIN book_author ON author.id = book_author.authorId WHERE book_author.bookId = :bookId";
    private static final String DELETE_BOOK_AUTHOR_RELATIONSHIP_SQL = "DELETE FROM book_author WHERE bookId = :bookId AND authorId = :authorId";

    public void addBookAuthorRelationship(BookAuthorDto bookAuthorDto) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("bookId", bookAuthorDto.getBookId());
            parameters.addValue("authorId", bookAuthorDto.getAuthorId());

            jdbcTemplate.update(INSERT_BOOK_AUTHOR_SQL, parameters);
        } catch (Exception e) {
            log.error("Ошибка добавления связи между книгой и автором: ", e);
        }
    }

    public void removeBookAuthorRelationship(BookAuthorDto bookAuthorDto) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("bookId", bookAuthorDto.getBookId());
            parameters.addValue("authorId", bookAuthorDto.getAuthorId());

            jdbcTemplate.update(DELETE_BOOK_AUTHOR_RELATIONSHIP_SQL, parameters);
        } catch (Exception e) {
            log.error("Ошибка удаления связи между книгой и автором: ", e);
        }
    }

    public List<AuthorDto> getAuthorsForBook(long bookId) {
        List<AuthorDto> authors = new ArrayList<>();

        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("bookId", bookId);

            authors = jdbcTemplate.query(SELECT_AUTHORS_FOR_BOOK, parameters, (resultSet, rowNum) ->
                    new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name"))
            );
        } catch (Exception e) {
            log.error("Ошибка при получении списка авторов для книги: ", e);
        }

        return authors;
    }
}
