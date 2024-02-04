package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<BookEntity> getAll();

    Optional<BookEntity> getById(Integer id);

    Optional<BookEntity> add(BookEntity book);

    Optional<BookEntity> edit(BookEntity book);

    Optional<BookEntity> deleteById(Integer id);

    List<BookEntity> getAllByFilter(String searchText, String searchType, List<Integer> authorSelect, Integer pagesFrom, Integer pagesTo);
}
