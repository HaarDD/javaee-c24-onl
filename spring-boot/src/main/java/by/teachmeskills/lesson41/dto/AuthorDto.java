package by.teachmeskills.lesson41.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AuthorDto {

    private Integer id;

    @Size(max = 50, min = 2)
    private String name;
}
