package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.entity.RoleEntity;
import by.teachmeskills.lesson41.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }

}
