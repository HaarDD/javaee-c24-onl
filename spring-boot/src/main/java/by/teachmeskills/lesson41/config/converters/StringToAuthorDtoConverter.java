package by.teachmeskills.lesson41.config.converters;

import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.service.AuthorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StringToAuthorDtoConverter implements Converter<String, AuthorDto> {

    private final AuthorsService authorsService;

    @Override
    public AuthorDto convert(String source) {
        return authorsService.getAuthorById(Integer.parseInt(source));
    }

}
