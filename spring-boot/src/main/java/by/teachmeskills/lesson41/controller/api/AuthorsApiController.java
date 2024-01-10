package by.teachmeskills.lesson41.controller.api;


import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.service.AuthorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthorDto> getAuthorById(@RequestParam Long id) {
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
    public void deleteAuthor(@RequestParam Long id) {
        authorsService.deleteAuthor(id);
    }
}
