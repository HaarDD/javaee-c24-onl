package by.teachmeskills.lesson41.controller.api;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.service.AuthorsService;
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
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorsApiController {

    private final AuthorsService authorsService;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> getAuthorsByIds() {
        return ResponseEntity.ok(authorsService.getAllAuthors());
    }

    @GetMapping("/exist")
    @ResponseStatus(HttpStatus.OK)
    public void isAuthorExist(@RequestParam String name) {
        authorsService.isAuthorExist(name);
    }

    @GetMapping
    public ResponseEntity<AuthorDto> getAuthorById(@RequestParam Integer id) {
        return ResponseEntity.ok(authorsService.getAuthorById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@ModelAttribute @Valid AuthorDto authorDto) {
        authorsService.addAuthor(authorDto);
    }

    @PutMapping
    public void editAuthor(@ModelAttribute @Valid AuthorDto authorDto) {
        authorsService.editAuthor(authorDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@RequestParam Integer id) {
        authorsService.deleteAuthor(id);
    }
}
