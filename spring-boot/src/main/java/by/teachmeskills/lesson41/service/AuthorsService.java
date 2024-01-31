package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.converter.AuthorConverter;
import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.entity.AuthorEntity;
import by.teachmeskills.lesson41.exception.ResourceAlreadyExistException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.repository.HibernateAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorsService {

    private final HibernateAuthorRepository authorsRepository;

    private final AuthorConverter authorConverter;

    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authorDtoList = authorsRepository.getAll();
        if (authorDtoList.isEmpty()) {
            throw new ResourceNotFoundException("Автор(ы) не найден(ы)!");
        }
        return authorDtoList.stream().map(authorConverter::convertToDto).toList();
    }

    public void isAuthorExist(String name) {
        authorsRepository.getByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Автора с именем %s не существует!".formatted(name)));
    }

    public AuthorDto getAuthorById(Integer id) {
        return authorConverter.convertToDto(authorsRepository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id))));
    }

    @Transactional
    public void addAuthor(AuthorDto authorDto) {
        authorsRepository.add(authorConverter.convertToEntity(authorDto))
                .orElseThrow(() -> new ResourceAlreadyExistException("Автор с таким именем уже существует", authorDto));
    }

    @Transactional
    public void editAuthor(AuthorDto authorDto) {
        authorsRepository.edit(authorConverter.convertToEntity(authorDto))
                .orElseThrow(() -> new ResourceNotFoundException("Автор %s не найден!".formatted(authorDto.getId())));
    }

    @Transactional
    public void deleteAuthor(Integer id) {
        authorsRepository.deleteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id)));
    }
}