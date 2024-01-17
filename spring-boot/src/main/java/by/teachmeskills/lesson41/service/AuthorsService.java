package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.exception.ResourceAlreadyExistException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.repositories.AuthorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsService {

    private final AuthorsRepository authorsRepository;

    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authorDtoList = authorsRepository.getAllAuthors();
        if (authorDtoList.isEmpty()) {
            throw new ResourceNotFoundException("Автор(ы) не найден(ы)!");
        }
        return authorDtoList;
    }

    public void isAuthorExist(String name) {
        authorsRepository.getAuthorByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Автора с именем %s не существует!".formatted(name)));
    }

    public AuthorDto getAuthorById(Long id) {
        return authorsRepository.getAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id)));
    }

    public void addAuthor(@Valid AuthorDto authorDto) {
        authorsRepository.addAuthor(authorDto)
                .orElseThrow(() -> new ResourceAlreadyExistException("Автор с таким именем уже существует", authorDto));
    }

    public void editAuthor(@Valid AuthorDto authorDto) {
        authorsRepository.editAuthor(authorDto)
                .orElseThrow(() -> new ResourceNotFoundException("Автор %s не найден!".formatted(authorDto.getId())));
    }

    public void deleteAuthor(Long id) {
        authorsRepository.deleteAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id)));
    }
}