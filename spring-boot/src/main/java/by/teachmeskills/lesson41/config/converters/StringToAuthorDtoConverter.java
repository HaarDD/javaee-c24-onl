package by.teachmeskills.lesson41.config.converters;

import by.teachmeskills.lesson41.entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class StringToAuthorDtoConverter implements Formatter<Author> {

    @Override
    public Author parse(String text, Locale locale) throws ParseException {
        Integer id = Integer.parseInt(text);
        Author author = new Author();
        author.setId(id);
        return author;
    }

    @Override
    public String print(Author object, Locale locale) {
        return null;
    }
}
