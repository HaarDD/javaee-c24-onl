package by.teachmeskills.lesson41.repository;


import by.teachmeskills.lesson41.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class HibernateAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public HibernateAuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> getById(Integer authorId) {
        return Optional.ofNullable(entityManager.find(Author.class, authorId));
    }

    @Override
    public Optional<Author> getByName(String name) {
        List<Author> authors = entityManager.createQuery("select a from Author a where a.name = :name", Author.class).setParameter("name", name).getResultList();
        return authors.isEmpty() ? Optional.empty() : Optional.of(authors.get(0));

    }

    @Override
    public Optional<Author> add(Author author) {
        try {
            entityManager.persist(author);
            return Optional.of(author);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> edit(Author author) {
        try {
            entityManager.merge(author);
            return Optional.of(author);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> deleteById(Integer authorId) {
        Optional<Author> optionalAuthor = getById(authorId);
        optionalAuthor.ifPresent(entityManager::remove);
        return optionalAuthor;
    }

    @Override
    public List<Author> getAllByIds(List<Integer> authorsIds) {
        return entityManager
                .createQuery("SELECT a FROM Author a WHERE a.id IN :ids", Author.class)
                .setParameter("ids", authorsIds)
                .getResultList();
    }
}
