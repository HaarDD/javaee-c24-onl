package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.converter.BookConverter;
import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.repository.HibernateBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BooksService {

    private final HibernateBookRepository booksRepository;

    private final BookConverter bookConverter;

    public BookDto getBookById(Integer id) {
        return bookConverter.convertToDto(booksRepository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id))));
    }

    @Transactional
    public void addBook(BookDto bookDto) {
        booksRepository.add(bookConverter.convertToEntity(bookDto))
                .orElseThrow(() -> new ResourceNotCreatedException("Ошибка создания книги!", bookDto));
    }

    @Transactional
    public void editBook(BookDto bookDto) {
        booksRepository.edit(bookConverter.convertToEntity(bookDto))
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(bookDto.getId())));
    }

    @Transactional
    public void deleteBook(Integer id) {
        booksRepository.deleteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id)));
    }

    public List<BookDto> getFilteredBooks(String searchText, String searchType, List<Integer> authorSelect, Integer pagesFrom, Integer pagesTo) {
        return booksRepository.getAllByFilter(searchText, searchType, authorSelect, pagesFrom, pagesTo).stream().map(bookConverter::convertToDto).toList();
    }

}
