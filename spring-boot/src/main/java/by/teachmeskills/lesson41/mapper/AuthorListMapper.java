package by.teachmeskills.lesson41.mapper;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.entity.AuthorEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = AuthorMapper.class)
public interface AuthorListMapper {

    List<AuthorDto> toDtoList(List<AuthorEntity> authorEntityList);

    List<AuthorEntity> toEntityList(List<AuthorDto> authorDtoList);

}
