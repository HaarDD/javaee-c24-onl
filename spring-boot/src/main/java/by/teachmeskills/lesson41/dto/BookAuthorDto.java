package by.teachmeskills.lesson41.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookAuthorDto {
    private Long bookId;
    private Long authorId;
}
