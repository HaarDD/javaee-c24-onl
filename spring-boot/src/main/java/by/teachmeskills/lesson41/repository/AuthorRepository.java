package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> getAll();

    Optional<Author> getById(Integer authorId);

    Optional<Author> getByName(String name);

    Optional<Author> add(Author authorDto);

    Optional<Author> edit(Author authorDto);

    Optional<Author> deleteById(Integer authorId);

    List<Author> getAllByIds(List<Integer> authorsIds);
}
