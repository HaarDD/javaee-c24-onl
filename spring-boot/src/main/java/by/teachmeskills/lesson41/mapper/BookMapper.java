package by.teachmeskills.lesson41.mapper;


import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.entity.BookEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {AuthorListMapper.class, BookFileMapper.class})
public interface BookMapper {

    BookDto toDto(BookEntity bookEntity);

    BookEntity toEntity(BookDto bookDto);

}
