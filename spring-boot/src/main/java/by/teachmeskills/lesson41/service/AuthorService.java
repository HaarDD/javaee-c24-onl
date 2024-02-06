package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.entity.AuthorEntity;
import by.teachmeskills.lesson41.exception.ResourceAlreadyExistException;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotDeletedException;
import by.teachmeskills.lesson41.exception.ResourceNotEditedException;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import by.teachmeskills.lesson41.mapper.AuthorListMapper;
import by.teachmeskills.lesson41.mapper.AuthorMapper;
import by.teachmeskills.lesson41.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorsRepository;

    private final AuthorMapper authorMapper;

    private final AuthorListMapper authorListMapper;

    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authorEntityList = authorsRepository.findAll();
        if (authorEntityList.isEmpty()) {
            throw new ResourceNotFoundException("Автор(ы) не найден(ы)!");
        }
        return authorListMapper.toDtoList(authorEntityList);
    }

    public void isAuthorExist(String name) {
        authorsRepository.getByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Автора с именем %s не существует!".formatted(name)));
    }

    public AuthorDto getAuthorById(Integer id) {
        return authorMapper.toDto(authorsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор с id %s не найден!".formatted(id))));
    }

    public void addAuthor(AuthorDto authorDto) {
        if(authorsRepository.existsByNameIgnoreCase(authorDto.getName())){
            throw new ResourceAlreadyExistException("Автор с именем \"%s\" уже существует".formatted(authorDto.getName()));
        } else{
            try {
                authorsRepository.save(authorMapper.toEntity(authorDto.setId(0)));
            } catch (Exception e) {
                throw new ResourceNotCreatedException("Ошибка создания автора!", authorDto);
            }
        }

    }

    public void editAuthor(AuthorDto authorDto) {
        try {
            authorsRepository.save(authorMapper.toEntity(authorDto));
        } catch (Exception e) {
            throw new ResourceNotEditedException("Ошибка редактирования автора!", authorDto);
        }
    }

    public void deleteAuthor(Integer id) {
        try {
            authorsRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotDeletedException("Ошибка удаления автора с id %s!".formatted(id));
        }
    }
}