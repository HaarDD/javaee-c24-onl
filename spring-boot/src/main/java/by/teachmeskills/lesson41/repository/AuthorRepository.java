package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<AuthorEntity> getAll();

    Optional<AuthorEntity> getById(Integer authorId);

    Optional<AuthorEntity> getByName(String name);

    Optional<AuthorEntity> add(AuthorEntity authorDto);

    Optional<AuthorEntity> edit(AuthorEntity authorDto);

    Optional<AuthorEntity> deleteById(Integer authorId);

    List<AuthorEntity> getAllByIds(List<Integer> authorsIds);
}
