package by.teachmeskills.lesson41.controller.api;

import by.teachmeskills.lesson41.dao.BooksRepository;
import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BooksApiController {

    private final BooksRepository booksRepository;

    @GetMapping
    @ResponseBody
    public BookDto getBookById(@RequestParam Long id) {
        return booksRepository.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@ModelAttribute @Valid BookDto bookDto) {
        booksRepository.addBook(bookDto)
                .orElseThrow(() -> new ResourceNotCreatedException("Ошибка создания книги!", bookDto));
    }

    @PutMapping
    public void editBook(@ModelAttribute @Valid BookDto bookDto) {
        booksRepository.editBook(bookDto)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(bookDto.getId())));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Long id) {
        booksRepository.deleteBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга с id %s не найдена!".formatted(id)));
    }

}
