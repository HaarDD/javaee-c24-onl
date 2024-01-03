package by.teachmeskills.lesson39.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;
    @Size(max = 50, min = 2)
    private String name;
}
