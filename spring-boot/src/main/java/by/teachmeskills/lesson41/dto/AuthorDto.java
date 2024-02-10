package by.teachmeskills.lesson41.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AuthorDto {

    private Integer id;

    @Size(max = 50, min = 2)
    private String name;
}
