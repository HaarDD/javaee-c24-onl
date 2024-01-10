package by.teachmeskills.lesson41.controller.api;

import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BooksApiController {

    private final BooksService booksService;

    @GetMapping
    public ResponseEntity<BookDto> getBookById(@RequestParam Long id) {
        BookDto bookDto = booksService.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@ModelAttribute @Valid BookDto bookDto) {
        booksService.addBook(bookDto);
    }

    @PutMapping
    public void editBook(@ModelAttribute @Valid BookDto bookDto) {
        booksService.editBook(bookDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Long id) {
        booksService.deleteBook(id);
    }
}
