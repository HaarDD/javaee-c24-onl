package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.entity.AuthorEntity;
import by.teachmeskills.lesson41.exception.ResourceAlreadyExistException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.mapper.AuthorMapper;
import by.teachmeskills.lesson41.repository.HibernateAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {

    private final HibernateAuthorRepository authorsRepository;

    private final AuthorMapper authorMapper;

    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authorDtoList = authorsRepository.getAll();
        if (authorDtoList.isEmpty()) {
            throw new ResourceNotFoundException("Автор(ы) не найден(ы)!");
        }
        return authorDtoList.stream().map(authorMapper::toDto).toList();
    }

    public void isAuthorExist(String name) {
        authorsRepository.getByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Автора с именем %s не существует!".formatted(name)));
    }

    public AuthorDto getAuthorById(Integer id) {
        return authorMapper.toDto(authorsRepository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id))));
    }

    @Transactional
    public void addAuthor(AuthorDto authorDto) {
        authorsRepository.add(authorMapper.toEntity(authorDto))
                .orElseThrow(() -> new ResourceAlreadyExistException("Автор с таким именем уже существует", authorDto));
    }

    @Transactional
    public void editAuthor(AuthorDto authorDto) {
        authorsRepository.edit(authorMapper.toEntity(authorDto))
                .orElseThrow(() -> new ResourceNotFoundException("Автор %s не найден!".formatted(authorDto.getId())));
    }

    @Transactional
    public void deleteAuthor(Integer id) {
        authorsRepository.deleteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id)));
    }
}