package by.teachmeskills.lesson41.controller.api;

import by.teachmeskills.lesson41.entity.Book;
import by.teachmeskills.lesson41.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BooksApiController {

    private final BooksService booksService;

    @GetMapping
    public ResponseEntity<Book> getBookById(@RequestParam Integer id) {
        Book bookDto = booksService.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@ModelAttribute @Valid Book book) {
        booksService.addBook(book);
    }

    @PutMapping
    public void editBook(@ModelAttribute @Valid Book book) {
        log.info("Попытка изменения: {}", book);
        booksService.editBook(book);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Integer id) {
        booksService.deleteBook(id);
    }
}
