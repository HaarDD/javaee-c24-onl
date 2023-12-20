package by.teachmeskills.lesson37.auth.session;


import by.teachmeskills.lesson37.auth.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;

@Data
@Scope("prototype")
@AllArgsConstructor
public class Session {

    private final String sessionId;

    private final String username;

    private final AuthorizationService authorizationService;
}
