package by.teachmeskills.lesson41.service;

import by.teachmeskills.lesson41.dto.RegisterUserDto;
import by.teachmeskills.lesson41.dto.UserDto;
import by.teachmeskills.lesson41.entity.UserEntity;
import by.teachmeskills.lesson41.exception.ResourceNotCreatedException;
import by.teachmeskills.lesson41.exception.ResourceNotEditedException;
import by.teachmeskills.lesson41.mapper.UserMapper;
import by.teachmeskills.lesson41.repository.UserRepository;
import by.teachmeskills.lesson41.security.UserAuthorities;
import by.teachmeskills.lesson41.security.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAll() {
        return userMapper.toDtos(userRepository.findAll());
    }

    public List<UserDto> getAll(Pageable pageable) {
        return userMapper.toDtos(userRepository.findContent(pageable));
    }

    public UserDto getById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Пользователь с идентификатором %d не найден".formatted(id)));
    }

    public UserDto addUserWithRole(RegisterUserDto userDto) {
        UserEntity userEntity = userMapper.toEntity(userDto.setStatus(UserStatus.OK.name()), roleService, passwordEncoder);
        userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    public UserDto addUserDefault(RegisterUserDto userDto) {
        UserEntity userEntity = userMapper.toEntity(userDto.setStatus(UserStatus.OK.name()).setRole(UserAuthorities.READER), roleService, passwordEncoder);
        userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    public void editUser(RegisterUserDto userDto) {
        try {
            userRepository.save(userMapper.toEntity(userDto, roleService, passwordEncoder));
        } catch (Exception e) {
            throw new ResourceNotEditedException("Ошибка обновления данных пользователя!", userDto);
        }
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }


}
