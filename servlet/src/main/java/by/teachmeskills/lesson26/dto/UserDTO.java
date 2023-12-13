package by.teachmeskills.lesson26.dto;

import by.teachmeskills.lesson26.dao.UserDAO;
import by.teachmeskills.lesson26.validation.ValidationResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String passwordHash;
    private String passwordSalt;
    private Long roleId;

    @Getter
    @Setter
    @AllArgsConstructor
    public class UserLoginValidationRequest {

        private final String email;
        private final String password;

        @JsonIgnore
        public ValidationResponse getValidationResponse() {
            return new UserLoginValidationResponse(UserDAO.emailExists(email), UserDAO.checkUserPassword(email, password));
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class UserLoginValidationResponse implements ValidationResponse {
        private final Boolean email;
        private final Boolean password;

        @Override
        public boolean isValid() {
            return email & password;
        }
    }


}
