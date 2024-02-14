package by.teachmeskills.lesson41.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthorities {

    public static final String READER = "READER";

    public static final String LIBRARIAN = "LIBRARIAN";

    public static final String MANAGER = "MANAGER";

    public boolean isReader(){
        return isRole(READER);
    }

    public boolean isLibrarian(){
        return isRole(LIBRARIAN);
    }

    public boolean isManager(){
        return isRole(MANAGER);
    }

    private static boolean isRole(String role){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).anyMatch(role::equals);
    }

}
