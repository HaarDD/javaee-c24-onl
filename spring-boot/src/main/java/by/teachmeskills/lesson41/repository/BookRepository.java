package by.teachmeskills.lesson41.repository;

import by.teachmeskills.lesson41.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAll();

    Optional<Book> getById(Integer id);

    Optional<Book> add(Book book);

    Optional<Book> edit(Book book);

    Optional<Book> deleteById(Integer id);

    List<Book> getAllByFilter(String searchText, String searchType, List<Integer> authorSelect, Integer pagesFrom, Integer pagesTo);
}
