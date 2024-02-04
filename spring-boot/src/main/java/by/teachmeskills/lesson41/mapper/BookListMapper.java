package by.teachmeskills.lesson41.mapper;

import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.entity.BookEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = BookMapper.class)
public interface BookListMapper {

    List<BookDto> toDtoList(List<BookEntity> bookEntityList);

    List<BookEntity> toEntityList(List<BookDto> bookDtoList);

}
