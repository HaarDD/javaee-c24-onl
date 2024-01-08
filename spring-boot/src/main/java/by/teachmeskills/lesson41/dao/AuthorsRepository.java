package by.teachmeskills.lesson41.dao;

import by.teachmeskills.lesson41.dbservice.DatabaseManager;
import by.teachmeskills.lesson41.dto.AuthorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthorsRepository {
    private static final String INSERT_AUTHOR_SQL = "INSERT INTO author (name) VALUES (:name)";
    private static final String SELECT_ALL_AUTHORS_SQL = "SELECT * FROM author";
    private static final String DELETE_AUTHOR_SQL = "DELETE FROM author WHERE id = :id";
    private static final String SELECT_AUTHOR_BY_NAME_SQL = "SELECT * FROM author WHERE name = :name";
    private static final String SELECT_AUTHOR_BY_ID_SQL = "SELECT * FROM author WHERE id = :id";
    private static final String UPDATE_AUTHOR_SQL = "UPDATE author SET name = :name WHERE id = :id";
    private static final String SELECT_AUTHORS_BY_IDS_SQL = "SELECT * FROM author WHERE id IN (:ids)";

    public List<AuthorDto> getAuthorsByIds(List<Long> authorsIds) {
        List<AuthorDto> authors = new ArrayList<>();

        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("ids", authorsIds);

            authors = jdbcTemplate.query(SELECT_AUTHORS_BY_IDS_SQL, parameters, (resultSet, rowNum) ->
                    new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    )
            );
            log.error("НЕ НАЙДЕНЫ: {}", Arrays.toString(authors.toArray()));
        } catch (Exception e) {
            log.error("Ошибка получения списка авторов по идентификаторам: ", e);
        }

        return authors;
    }

    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authors = new ArrayList<>();

        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            authors = jdbcTemplate.query(SELECT_ALL_AUTHORS_SQL, (resultSet, rowNum) ->
                    new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    )
            );
        } catch (Exception e) {
            log.error("Ошибка получения списка авторов: ", e);
        }

        return authors;
    }

    public Optional<AuthorDto> addAuthor(AuthorDto authorDto) {
        if (getAuthorByName(authorDto.getName()).isPresent()) {
            log.warn("Автор с именем {} уже существует", authorDto.getName());
            return Optional.empty();
        }

        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", authorDto.getName());

            jdbcTemplate.update(INSERT_AUTHOR_SQL, parameters);

            return getAuthorByName(authorDto.getName());
        } catch (Exception e) {
            log.error("Ошибка добавления автора: ", e);
        }

        return Optional.empty();
    }

    public Optional<AuthorDto> getAuthorByName(String name) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", name);

            List<AuthorDto> authors = jdbcTemplate.query(SELECT_AUTHOR_BY_NAME_SQL, parameters, (resultSet, rowNum) ->
                    new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    )
            );

            return authors.isEmpty() ? Optional.empty() : Optional.of(authors.get(0));
        } catch (Exception e) {
            log.error("Ошибка проверки существования автора: ", e);
        }
        return Optional.empty();
    }

    public Optional<AuthorDto> deleteAuthorById(Long authorId) {
        Optional<AuthorDto> optionalAuthorDto = getAuthorById(authorId);
        if (optionalAuthorDto.isEmpty()) {
            log.warn("Автора с id \"{}\" не существует", authorId);
            return Optional.empty();
        }

        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", authorId);

            jdbcTemplate.update(DELETE_AUTHOR_SQL, parameters);
        } catch (Exception e) {
            log.error("Ошибка удаления автора: ", e);
            return Optional.empty();
        }

        return optionalAuthorDto;
    }

    public Optional<AuthorDto> getAuthorById(Long authorId) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", authorId);

            List<AuthorDto> authors = jdbcTemplate.query(SELECT_AUTHOR_BY_ID_SQL, parameters, (resultSet, rowNum) ->
                    new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    )
            );

            return authors.isEmpty() ? Optional.empty() : Optional.of(authors.get(0));
        } catch (Exception e) {
            log.error("Ошибка получения автора по идентификатору: ", e);
        }
        return Optional.empty();
    }

    public Optional<AuthorDto> editAuthor(AuthorDto authorDto) {
        if (getAuthorById(authorDto.getId()).isEmpty()) {
            log.warn("Автора с id \"{}\" не существует", authorDto.getId());
            return Optional.empty();
        }

        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(DatabaseManager.getDataSource());
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", authorDto.getId());
            parameters.addValue("name", authorDto.getName());

            jdbcTemplate.update(UPDATE_AUTHOR_SQL, parameters);

            return Optional.of(authorDto);
        } catch (Exception e) {
            log.error("Ошибка редактирования автора: ", e);
            return Optional.empty();
        }
    }
}
