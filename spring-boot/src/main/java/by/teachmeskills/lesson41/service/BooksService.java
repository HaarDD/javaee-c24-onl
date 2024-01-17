package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.repositories.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    public BookDto getBookById(Long id) {
        return booksRepository.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id)));
    }

    public void addBook(@Valid BookDto bookDto) {
        booksRepository.addBook(bookDto)
                .orElseThrow(() -> new ResourceNotCreatedException("Ошибка создания книги!", bookDto));
    }

    public void editBook(@Valid BookDto bookDto) {
        booksRepository.editBook(bookDto)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(bookDto.getId())));
    }

    public void deleteBook(Long id) {
        booksRepository.deleteBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id)));
    }

    public List<BookDto> getFilteredBooks(String searchText, String searchType, List<Long> authorSelect, Long pagesFrom, Long pagesTo) {
        return booksRepository.getFilteredBooks(searchText, searchType, authorSelect, pagesFrom, pagesTo);
    }

}
