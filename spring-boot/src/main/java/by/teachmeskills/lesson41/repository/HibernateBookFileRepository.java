package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.BookEntity;
import by.teachmeskills.lesson41.entity.BookFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
@Slf4j
@Transactional
public class HibernateBookFileRepository implements BookFileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public HibernateBookFileRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<BookFileEntity> getByBookId(Integer bookId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookFileEntity> query = builder.createQuery(BookFileEntity.class);
        Root<BookFileEntity> root = query.from(BookFileEntity.class);

        Join<BookFileEntity, BookEntity> bookJoin = root.join("book");
        query.select(root).where(builder.equal(bookJoin.get("id"), bookId));

        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    @Override
    public Optional<BookFileEntity> add(BookFileEntity bookFileEntity) {
        entityManager.persist(bookFileEntity);
        return Optional.of(bookFileEntity);
    }

    @Override
    public Optional<BookFileEntity> deleteByBookId(Integer bookId) {
        Optional<BookFileEntity> optionalBookFileEntity = getByBookId(bookId);
        optionalBookFileEntity.ifPresent(entityManager::remove);
        return optionalBookFileEntity;
    }
}
