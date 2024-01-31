package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.BookEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class HibernateBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public HibernateBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BookEntity> getAll() {
        return entityManager.createQuery("select b from BookEntity b", BookEntity.class).getResultList();
    }

    @Override
    public Optional<BookEntity> getById(Integer id) {
        return Optional.ofNullable(entityManager.find(BookEntity.class, id));
    }

    @Override
    public Optional<BookEntity> add(BookEntity book) {
        try {
            entityManager.persist(book);
            return Optional.of(book);
        } catch (Exception e) {
            log.error("Ошибка в ходе добавления книги {}", book, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<BookEntity> edit(BookEntity book) {
        try {
            entityManager.merge(book);
            return Optional.of(book);
        } catch (Exception e) {
            log.error("Ошибка в ходе изменения книги книги {}", book, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<BookEntity> deleteById(Integer id) {
        Optional<BookEntity> optionalBook = getById(id);
        optionalBook.ifPresent(entityManager::remove);
        return optionalBook;
    }

    @Override
    public List<BookEntity> getAllByFilter(String searchText, String searchType, List<Integer> authorSelect, Integer pagesFrom, Integer pagesTo) {
        String sql = """
                SELECT DISTINCT b FROM BookEntity b
                LEFT JOIN b.authors a
                WHERE (:searchText IS NULL OR
                (:searchType = 'name' AND LOWER(b.name) LIKE LOWER(CONCAT('%', :searchText, '%'))) OR
                (:searchType = 'description' AND LOWER(b.description) LIKE LOWER(CONCAT(:searchText, '%'))) OR
                (:searchType = 'isbn' AND LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchText, '%'))))
                AND (:authorSelectIsNull IS NULL OR a.id IN (:authorSelect))
                AND (:pagesFrom IS NULL OR b.pages > :pagesFrom)
                AND (:pagesTo IS NULL OR b.pages < :pagesTo)""";

        Query query = entityManager.createQuery(sql, BookEntity.class);

        query.setParameter("searchText", searchText);
        query.setParameter("searchType", searchType);
        query.setParameter("authorSelectIsNull", authorSelect != null ? "NOT_NULL" : null);
        query.setParameter("authorSelect", authorSelect);
        query.setParameter("pagesFrom", pagesFrom);
        query.setParameter("pagesTo", pagesTo);

        return query.getResultList();
    }
}
