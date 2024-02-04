package by.teachmeskills.lesson41.mapper;

import by.teachmeskills.lesson41.dto.BookFileDto;
import by.teachmeskills.lesson41.entity.BookFileEntity;
import org.mapstruct.Mapper;

@Mapper(uses = BookMapper.class)
public interface BookFileMapper {

    BookFileDto toDto(BookFileEntity bookFileEntity);

    BookFileEntity toEntity(BookFileDto bookFileDto);

}
