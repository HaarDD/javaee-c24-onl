package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    Optional<AuthorEntity> getByName(String name);

    Boolean existsByNameIgnoreCase(String name);

    List<AuthorEntity> getAllByIdIn(List<Integer> authorIds);
}
