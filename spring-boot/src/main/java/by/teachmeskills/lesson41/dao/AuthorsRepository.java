package by.teachmeskills.lesson41.dao;

import by.teachmeskills.lesson41.dbservice.DatabaseManager;
import by.teachmeskills.lesson41.dto.AuthorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class AuthorsRepository {
    private static final String INSERT_AUTHOR_SQL = "INSERT INTO author (name) VALUES (?)";
    private static final String SELECT_ALL_AUTHORS_SQL = "SELECT * FROM author";
    private static final String DELETE_AUTHOR_SQL = "DELETE FROM author WHERE id = ?";
    private static final String SELECT_AUTHOR_BY_NAME_SQL = "SELECT * FROM author WHERE name = ?";
    private static final String SELECT_AUTHOR_BY_ID_SQL = "SELECT * FROM author WHERE id = ?";
    private static final String UPDATE_AUTHOR_SQL = "UPDATE author SET name = ? WHERE id = ?";
    private static final String SELECT_AUTHORS_BY_IDS_SQL = "SELECT * FROM author WHERE id IN ";

    public List<AuthorDto> getAuthorsByIds(List<Long> authorsIds) {
        List<AuthorDto> authors = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection()) {
            StringBuilder queryBuilder = new StringBuilder();

            if (authorsIds == null || authorsIds.isEmpty()) {
                queryBuilder.append(SELECT_ALL_AUTHORS_SQL);
            } else {
                queryBuilder.append(SELECT_AUTHORS_BY_IDS_SQL);
                String idsString = authorsIds.stream().map(String::valueOf).collect(Collectors.joining(","));
                queryBuilder.append("(").append(idsString).append(")");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    AuthorDto author = new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    );
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка получения списка авторов по идентификаторам: ", e);
        }

        return authors;
    }


    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authors = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTHORS_SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    AuthorDto author = new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    );
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка получения списка авторов по идентификаторам: ", e);
        }

        return authors;
    }

    public Optional<AuthorDto> addAuthor(AuthorDto authorDto) {
        if (getAuthorByName(authorDto.getName()).isPresent()) {
            log.warn("Автор с именем {} уже существует", authorDto.getName());
            return Optional.empty();
        }

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, authorDto.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return Optional.of(authorDto.setId(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            log.error("Ошибка добавления автора: ", e);
        }

        return Optional.empty();
    }

    public Optional<AuthorDto> getAuthorByName(String name) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME_SQL)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
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
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_SQL)) {
            preparedStatement.setLong(1, authorId);
            preparedStatement.executeUpdate();
            return optionalAuthorDto;
        } catch (SQLException e) {
            log.error("Ошибка удаления автора: ", e);
        }
        return Optional.empty();
    }

    public Optional<AuthorDto> getAuthorById(Long authorId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID_SQL)) {
            preparedStatement.setLong(1, authorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new AuthorDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка получения автора по идентификатору: ", e);
        }
        return Optional.empty();
    }

    public Optional<AuthorDto> editAuthor(AuthorDto authorDto) {
        if (getAuthorById(authorDto.getId()).isEmpty()) {
            log.warn("Автора с id \"{}\" не существует", authorDto.getId());
            return Optional.empty();
        }
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_SQL)) {
            preparedStatement.setString(1, authorDto.getName());
            preparedStatement.setLong(2, authorDto.getId());
            preparedStatement.executeUpdate();
            return Optional.of(authorDto);
        } catch (SQLException e) {
            log.error("Ошибка редактирования автора: ", e);
        }
        return Optional.empty();
    }

}
