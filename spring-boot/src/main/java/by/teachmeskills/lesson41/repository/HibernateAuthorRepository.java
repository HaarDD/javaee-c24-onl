package by.teachmeskills.lesson41.repository;


import by.teachmeskills.lesson41.entity.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public HibernateAuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AuthorEntity> getAll() {
        return entityManager.createQuery("select a from AuthorEntity a", AuthorEntity.class).getResultList();
    }

    @Override
    public Optional<AuthorEntity> getById(Integer authorId) {
        return Optional.ofNullable(entityManager.find(AuthorEntity.class, authorId));
    }

    @Override
    public Optional<AuthorEntity> getByName(String name) {
        List<AuthorEntity> authors = entityManager.createQuery("select a from AuthorEntity a where a.name = :name", AuthorEntity.class).setParameter("name", name).getResultList();
        return authors.isEmpty() ? Optional.empty() : Optional.of(authors.get(0));

    }

    @Override
    public Optional<AuthorEntity> add(AuthorEntity author) {
        try {
            entityManager.persist(author);
            return Optional.of(author);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthorEntity> edit(AuthorEntity author) {
        try {
            entityManager.merge(author);
            return Optional.of(author);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthorEntity> deleteById(Integer authorId) {
        Optional<AuthorEntity> optionalAuthor = getById(authorId);
        optionalAuthor.ifPresent(entityManager::remove);
        return optionalAuthor;
    }

    @Override
    public List<AuthorEntity> getAllByIds(List<Integer> authorsIds) {
        return entityManager
                .createQuery("SELECT a FROM AuthorEntity a WHERE a.id IN :ids", AuthorEntity.class)
                .setParameter("ids", authorsIds)
                .getResultList();
    }
}
