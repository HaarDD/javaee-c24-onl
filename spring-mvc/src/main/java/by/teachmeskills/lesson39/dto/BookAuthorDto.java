package by.teachmeskills.lesson39.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookAuthorDto {
    private Long bookId;
    private Long authorId;
}
