package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("select user from UserEntity user")
    List<UserEntity> findContent(Pageable page);

    List<UserEntity> findAllByFullNameContainsIgnoreCase(String fullName);

    @EntityGraph(value = "User.role")
    Optional<UserEntity> findByLogin(String login);
}
