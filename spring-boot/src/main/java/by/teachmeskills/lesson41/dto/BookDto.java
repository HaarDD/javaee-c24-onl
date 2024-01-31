package by.teachmeskills.lesson41.dto;

import by.teachmeskills.lesson41.entity.BookFileEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class BookDto {

    private Integer id;

    @NotNull
    @NotEmpty
    @Size(max = 150, min = 2)
    private String name;

    @Size(max = 10000)
    private String description;

    @ISBN
    private String isbn;

    @Min(1)
    @Max(5000)
    private Integer pages;

    private List<AuthorDto> authors;

    private BookFileEntity bookFile;
}
