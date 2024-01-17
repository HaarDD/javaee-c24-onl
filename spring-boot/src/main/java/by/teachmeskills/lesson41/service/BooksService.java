package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.entity.Book;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.repository.HibernateBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final HibernateBookRepository booksRepository;

    public Book getBookById(Integer id) {
        return booksRepository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id)));
    }

    public void addBook(@Valid Book book) {
        booksRepository.add(book)
                .orElseThrow(() -> new ResourceNotCreatedException("Ошибка создания книги!", book));
    }

    public void editBook(@Valid Book book) {
        booksRepository.edit(book)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(book.getId())));
    }

    public void deleteBook(Integer id) {
        booksRepository.deleteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id)));
    }

    public List<Book> getFilteredBooks(String searchText, String searchType, List<Integer> authorSelect, Integer pagesFrom, Integer pagesTo) {
        return booksRepository.getAllByFilter(searchText, searchType, authorSelect, pagesFrom, pagesTo);
    }

}
