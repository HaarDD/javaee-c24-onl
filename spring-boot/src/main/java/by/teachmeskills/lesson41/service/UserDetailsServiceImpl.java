package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.repository.UserRepository;
import by.teachmeskills.lesson41.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return userRepository.findByLogin(login).map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким логином (%s) не найден!".formatted(login)));
    }
}
