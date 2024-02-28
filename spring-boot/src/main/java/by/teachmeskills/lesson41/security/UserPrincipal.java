package by.teachmeskills.lesson41.security;

import by.teachmeskills.lesson41.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !UserStatus.LOGIN_EXPIRED.equals(user.getStatus());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !UserStatus.BLOCKED.equals(user.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !UserStatus.PASSWORD_EXPIRED.equals(user.getStatus());
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.OK.equals(user.getStatus());
    }
}
