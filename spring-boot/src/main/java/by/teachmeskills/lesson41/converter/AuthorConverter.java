package by.teachmeskills.lesson41.converter;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.entity.AuthorEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    private final ModelMapper modelMapper;

    public AuthorConverter() {
        this.modelMapper = new ModelMapper();
    }

    public AuthorDto convertToDto(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    public AuthorEntity convertToEntity(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }

}
