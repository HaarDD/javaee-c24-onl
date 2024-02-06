package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    @Query("""
           SELECT DISTINCT b FROM BookEntity b
           LEFT JOIN b.authors a
           WHERE (:searchText IS NULL OR
           (:searchType = 'name' AND LOWER(b.name) LIKE LOWER(CONCAT(:searchText, '%'))) OR
           (:searchType = 'description' AND LOWER(b.description) LIKE LOWER(CONCAT(:searchText, '%'))) OR
           (:searchType = 'isbn' AND LOWER(b.isbn) LIKE LOWER(CONCAT(:searchText, '%'))))
           AND (:authorSelectIsNull IS TRUE OR a.id IN :authorSelect)
           AND (:pagesFrom IS NULL OR b.pages > :pagesFrom)
           AND (:pagesTo IS NULL OR b.pages < :pagesTo)
           """)
    List<BookEntity> getAllByFilter(
            @Param("searchText") String searchText,
            @Param("searchType") String searchType,
            @Param("authorSelectIsNull") Boolean authorSelectIsNull,
            @Param("authorSelect") List<Integer> authorSelect,
            @Param("pagesFrom") Integer pagesFrom,
            @Param("pagesTo") Integer pagesTo
    );
}
