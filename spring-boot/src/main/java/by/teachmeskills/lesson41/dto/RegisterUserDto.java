package by.teachmeskills.lesson41.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class RegisterUserDto {

    private Integer id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String login;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

    @NotNull
    @NotEmpty
    @NotBlank
    private String fullName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String status;

    @Nullable
    private String role;

    private boolean active = true;

}
