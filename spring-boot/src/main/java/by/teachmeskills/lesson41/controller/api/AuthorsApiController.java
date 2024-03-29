package by.teachmeskills.lesson41.controller.api;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
@Tag(name = "Авторы", description = "Управление авторами")
public class AuthorsApiController {

    private final AuthorService authorsService;


    @PreAuthorize("hasAnyAuthority('READER','LIBRARIAN','MANAGER')")
    @Operation(summary = "Получить всех", description = "Позволяет получить всех авторов")
    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> getAuthorsByIds() {
        return ResponseEntity.ok(authorsService.getAllAuthors());
    }


    @PreAuthorize("hasAnyAuthority('READER','LIBRARIAN','MANAGER')")
    @Operation(summary = "Получить статус по имени", description = "Позволяет узнать, существует ли автор с таким именем")
    @GetMapping("/exist")
    @ResponseStatus(HttpStatus.OK)
    public void isAuthorExist(@RequestParam @Parameter(description = "Имя") String name) {
        authorsService.isAuthorExist(name);
    }

    @PreAuthorize("hasAnyAuthority('READER','LIBRARIAN','MANAGER')")
    @Operation(summary = "Получить", description = "Позволяет получить автора по id")
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable @Parameter(description = "Id получаемого автора", required = true) Integer authorId) {
        return ResponseEntity.ok(authorsService.getAuthorById(authorId));
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','MANAGER')")
    @Operation(summary = "Добавить", description = "Позволяет добавить одного автора")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@ModelAttribute @Valid @Parameter(description = "Модель автора") AuthorDto authorDto) {
        authorsService.addAuthor(authorDto);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','MANAGER')")
    @Operation(summary = "Редактировать", description = "Позволяет редактировать одного автора")
    @PutMapping("/{authorId}")
    public void editAuthor(@PathVariable @Parameter(description = "Id изменяемого автора", required = true) Integer authorId, @ModelAttribute @Valid @Parameter(description = "Модель автора") AuthorDto authorDto) {
        authorsService.editAuthor(authorDto.setId(authorId));
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','MANAGER')")
    @Operation(summary = "Удалить", description = "Позволяет удалить одного автора")
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable @Parameter(description = "Id удаляемого автора", required = true) Integer authorId) {
        authorsService.deleteAuthor(authorId);
    }
}
