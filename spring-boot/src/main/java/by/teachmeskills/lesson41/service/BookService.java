package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotDeletedException;
import by.teachmeskills.lesson41.exception.ResourceNotEditedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.mapper.BookListMapper;
import by.teachmeskills.lesson41.mapper.BookMapper;
import by.teachmeskills.lesson41.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    private final BookFileService bookFileService;

    private final BookMapper bookMapper;

    private final BookListMapper bookListMapper;

    public BookDto getBookById(Integer id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id))));
    }

    public void addBook(BookDto bookDto) {
        try {
            bookRepository.save(bookMapper.toEntity(bookDto.setId(0)));
        } catch (Exception e) {
            throw new ResourceNotCreatedException("Ошибка создания книги!", bookDto);
        }
    }

    public void editBook(BookDto bookDto) {
        try {
            bookRepository.save(bookMapper.toEntity(bookDto));
        } catch (Exception e) {
            throw new ResourceNotEditedException("Ошибка редактирования книги!", bookDto);
        }
    }

    public void deleteBook(Integer id) {
        try {
            bookRepository.deleteById(id);
            try {
                bookFileService.removeBookFileByBookId(id);
            } catch (ResourceNotFoundException e) {
                log.info("Запись о книге не найдена");
            }
        } catch (Exception e) {
            throw new ResourceNotDeletedException("Ошибка удаления книги с id: %s!".formatted(id));
        }
    }

    public List<BookDto> getFilteredBooks(String searchText, String searchType, List<Integer> authorSelect, Integer pagesFrom, Integer pagesTo) {
        return bookListMapper.toDtoList(bookRepository.getAllByFilter(searchText, searchType, authorSelect == null, authorSelect, pagesFrom, pagesTo));
    }

}
