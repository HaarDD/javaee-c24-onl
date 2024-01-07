package by.teachmeskills.lesson41.config.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;
import by.teachmeskills.lesson41.dto.AuthorDto;

import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class StringToAuthorDtoConverter implements Formatter<AuthorDto> {

    @Override
    public AuthorDto parse(String text, Locale locale) throws ParseException {
        Long id = Long.parseLong(text);
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        return authorDto;
    }

    @Override
    public String print(AuthorDto object, Locale locale) {
        return null;
    }
}
