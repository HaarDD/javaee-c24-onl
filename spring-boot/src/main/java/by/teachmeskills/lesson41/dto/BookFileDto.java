package by.teachmeskills.lesson41.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BookFileDto {

    private Integer id;

    @NotNull
    @NotEmpty
    private String fileName;
    @NotNull
    @NotEmpty
    private Integer fileSize;
    @NotNull
    @NotEmpty
    private String fileKey;
    @NotNull
    @NotEmpty
    private Timestamp uploadDate;

    private BookDto book;
}
