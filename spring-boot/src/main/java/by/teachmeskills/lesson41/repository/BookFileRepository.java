package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.BookFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookFileRepository extends JpaRepository<BookFileEntity, Integer> {

    @Query("SELECT bf FROM BookFileEntity bf JOIN bf.book b WHERE b.id = :bookId")
    Optional<BookFileEntity> getByBookId(@Param("bookId") Integer bookId);

    @Modifying
    void deleteByBookId(Integer bookId);
}
