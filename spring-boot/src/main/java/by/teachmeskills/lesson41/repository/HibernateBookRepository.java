package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class HibernateBookRepository implements BookRepository {


    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public HibernateBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> getById(Integer id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Optional<Book> add(Book book) {
        try {
            entityManager.persist(book);
            return Optional.of(book);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> edit(Book book) {
        try {
            entityManager.merge(book);
            return Optional.of(book);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> deleteById(Integer id) {
        Optional<Book> optionalBook = getById(id);
        optionalBook.ifPresent(entityManager::remove);
        return optionalBook;
    }


    //TODO написать тесты для разных комбинаций фильтров
    @Override
    public List<Book> getAllByFilter(String searchText, String searchType, List<Integer> authorSelect, Integer pagesFrom, Integer pagesTo) {

        String sql = "SELECT DISTINCT b FROM Book b " +
                "LEFT JOIN b.authors a " +
                "WHERE (:searchText IS NULL OR " +
                "(:searchType = 'name' AND LOWER(b.name) LIKE LOWER(CONCAT('%', :searchText, '%'))) OR " +
                "(:searchType = 'description' AND LOWER(b.description) LIKE LOWER(CONCAT('%', :searchText, '%'))) OR " +
                "(:searchType = 'isbn' AND LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchText, '%')))) " +
                "AND (:authorSelectIsNull IS NULL OR a.id IN (:authorSelect)) " +
                "AND (:pagesFrom IS NULL OR b.pages > :pagesFrom) " +
                "AND (:pagesTo IS NULL OR b.pages < :pagesTo)";

        javax.persistence.Query query = entityManager.createQuery(sql, Book.class);

        query.setParameter("searchText", searchText);
        query.setParameter("searchType", searchType);
        query.setParameter("authorSelectIsNull", authorSelect != null ? "NOT_NULL" : null);
        query.setParameter("authorSelect", authorSelect);
        query.setParameter("pagesFrom", pagesFrom);
        query.setParameter("pagesTo", pagesTo);

        return query.getResultList();
    }
}
