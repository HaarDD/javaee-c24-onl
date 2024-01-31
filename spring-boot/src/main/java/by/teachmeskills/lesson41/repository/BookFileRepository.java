package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.BookFileEntity;

import java.util.Optional;

public interface BookFileRepository {

    Optional<BookFileEntity> getByBookId(Integer bookId);

    Optional<BookFileEntity> add(BookFileEntity bookFileEntity);

    Optional<BookFileEntity> deleteByBookId(Integer bookId);

}
