package by.teachmeskills.lesson41.converter;

import by.teachmeskills.lesson41.dto.BookDto;
import by.teachmeskills.lesson41.entity.BookEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component

public class BookConverter {
    private final ModelMapper modelMapper;

    public BookConverter() {
        this.modelMapper = new ModelMapper();
    }

    public BookDto convertToDto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    public BookEntity convertToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }

}
