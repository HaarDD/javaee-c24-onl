package by.teachmeskills.lesson41.controller.api;

import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.service.BookFileService;
import by.teachmeskills.lesson41.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Книги", description = "Управление книгами и загрузка файлов")
public class BooksApiController {

    private final BookService booksService;

    private final BookFileService bookFilesService;

    @Operation(summary = "Получить", description = "Позволяет получить данные книги по id")
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Integer bookId) {
        BookDto bookDto = booksService.getBookById(bookId);
        return ResponseEntity.ok(bookDto);
    }

    @Operation(summary = "Добавить", description = "Позволяет добавить одну книгу, id игнорируется")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@ModelAttribute @Valid @Parameter(description = "Модель книги") BookDto bookDto) {
        booksService.addBook(bookDto);
    }

    @Operation(summary = "Редактировать", description = "Позволяет редактировать одну книгу")
    @PutMapping("/{bookId}")
    public void editBook(@PathVariable Integer bookId, @ModelAttribute @Valid @Parameter(description = "Модель книги") BookDto bookDto) {
        booksService.editBook(bookDto.setId(bookId));
    }

    @Operation(summary = "Удалить", description = "Позволяет удалить одну книгу")
    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Integer bookId) {
        booksService.deleteBook(bookId);
    }

    @Operation(summary = "Скачать файл книги", description = "Позволяет скачать файл книги по id книги")
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

    @Operation(summary = "Загрузить файл книги", description = "Позволяет загрузить файл книги и прикрепить его к книге")
    @PostMapping(path = "/{bookId}/bookfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookWithFile(@PathVariable Integer bookId, @Parameter(description = "Файл любого типа, до 20мб") @RequestPart(name = "file", required = true) MultipartFile bookFile) {
        bookFilesService.uploadBookFileByBookId(bookFile, bookId);
    }

    @Operation(summary = "Удалить файл книги", description = "Позволяет удалить файл книги по id книги")
    @DeleteMapping("/{bookId}/bookfile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookWithFile(@PathVariable Integer bookId) {
        bookFilesService.removeBookFileByBookId(bookId);
    }
}
