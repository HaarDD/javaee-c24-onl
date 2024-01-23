package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.entity.Author;
import by.teachmeskills.lesson41.exception.ResourceAlreadyExistException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.repository.HibernateAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsService {

    private final HibernateAuthorRepository authorsRepository;

    public List<Author> getAllAuthors() {
        List<Author> authorDtoList = authorsRepository.getAll();
        if (authorDtoList.isEmpty()) {
            throw new ResourceNotFoundException("Автор(ы) не найден(ы)!");
        }
        return authorDtoList;
    }

    public void isAuthorExist(String name) {
        authorsRepository.getByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Автора с именем %s не существует!".formatted(name)));
    }

    public Author getAuthorById(Integer id) {
        return authorsRepository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id)));
    }

    public void addAuthor(@Valid Author author) {
        authorsRepository.add(author)
                .orElseThrow(() -> new ResourceAlreadyExistException("Автор с таким именем уже существует", author));
    }

    public void editAuthor(@Valid Author author) {
        authorsRepository.edit(author)
                .orElseThrow(() -> new ResourceNotFoundException("Автор %s не найден!".formatted(author.getId())));
    }

    public void deleteAuthor(Integer id) {
        authorsRepository.deleteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id)));
    }
}