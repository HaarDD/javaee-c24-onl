package by.teachmeskills.lesson39.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private String description;
    private String isbn;
    private long pages;
    private List<AuthorDto> authors;
}
