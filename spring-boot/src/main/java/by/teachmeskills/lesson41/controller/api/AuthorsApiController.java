package by.teachmeskills.lesson41.controller.api;


import by.teachmeskills.lesson41.dao.AuthorsRepository;
import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.exception.ResourceAlreadyExistException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
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

    private final AuthorsRepository authorsRepository;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> getAuthorsByIds() {
        List<AuthorDto> authorDtoList = authorsRepository.getAllAuthors();
        if (authorDtoList.isEmpty()) {
            throw new ResourceNotFoundException("Автор(ы) не найден(ы)!");
        }
        return ResponseEntity.ok(authorDtoList);
    }

    @GetMapping("/exist")
    @ResponseStatus(HttpStatus.OK)
    public void isAuthorExist(@RequestParam String name) {
        authorsRepository.getAuthorByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Автора с именем %s не существует!".formatted(name)));
    }

    @GetMapping
    public ResponseEntity<AuthorDto> getAuthorById(@RequestParam Long id) {
        return ResponseEntity.ok(authorsRepository.getAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@ModelAttribute @Valid AuthorDto authorDto) {
        authorsRepository.addAuthor(authorDto)
                .orElseThrow(() -> new ResourceAlreadyExistException("Автор с таким именем уже существует", authorDto));
    }

    @PutMapping
    public void editAuthor(@ModelAttribute @Valid AuthorDto authorDto) {
        authorsRepository.editAuthor(authorDto)
                .orElseThrow(() -> new ResourceNotFoundException("Автор %s не найден!".formatted(authorDto.getId())));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@RequestParam Long id) {
        authorsRepository.deleteAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id)));
    }

}
