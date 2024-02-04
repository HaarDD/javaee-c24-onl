package by.teachmeskills.lesson41.controller.api;

import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.service.BookFileService;
import by.teachmeskills.lesson41.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BooksApiController {

    private final BookService booksService;

    private final BookFileService bookFilesService;

    @GetMapping
    public ResponseEntity<BookDto> getBookById(@RequestParam Integer bookId) {
        BookDto bookDto = booksService.getBookById(bookId);
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@ModelAttribute @Valid BookDto bookDto) {
        booksService.addBook(bookDto);
    }


    @GetMapping("/{bookId}/bookfile")
    public ResponseEntity<Resource> downloadBookFile(@PathVariable Integer bookId) {

        Resource resource = bookFilesService.getBookFileResourceByBookId(bookId);

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriUtils.encode(resource.getFilename(), StandardCharsets.UTF_8) + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/{bookId}/bookfile")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookWithFile(@PathVariable Integer bookId, @RequestPart("file") MultipartFile bookFile) {
        bookFilesService.uploadBookFileByBookId(bookFile, bookId);
    }

    @DeleteMapping("/{bookId}/bookfile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookWithFile(@PathVariable Integer bookId) {
        bookFilesService.removeBookFileByBookId(bookId);
    }

    @PutMapping
    public void editBook(@ModelAttribute @Valid BookDto bookDto) {
        booksService.editBook(bookDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Integer bookId) {
        booksService.deleteBook(bookId);
    }
}
