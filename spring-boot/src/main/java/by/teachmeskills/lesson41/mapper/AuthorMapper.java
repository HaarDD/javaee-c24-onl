package by.teachmeskills.lesson41.mapper;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.entity.AuthorEntity;
import org.mapstruct.Mapper;

@Mapper(uses = BookListMapper.class)
public interface AuthorMapper {

    AuthorDto toDto(AuthorEntity authorEntity);

    AuthorEntity toEntity(AuthorDto authorDto);

}
