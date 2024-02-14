package by.teachmeskills.lesson41.mapper;


import by.teachmeskills.lesson41.dto.RegisterUserDto;
import by.teachmeskills.lesson41.dto.UserDto;
import by.teachmeskills.lesson41.entity.UserEntity;
import by.teachmeskills.lesson41.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    UserDto toDto(UserEntity userEntity);

    @Mapping(target = "role", ignore = true)
    UserEntity toEntity(RegisterUserDto registerUserDto, @Context RoleService roleService, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void postConstruct(RegisterUserDto registerUserDto, @MappingTarget UserEntity userEntity,
                               @Context RoleService roleService, @Context PasswordEncoder passwordEncoder) {
        userEntity.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        roleService.findByName(registerUserDto.getRole()).ifPresent(userEntity::setRole);
    }

    default List<UserDto> toDtos(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(this::toDto).toList();
    }

    default List<UserEntity> toEntites(List<UserDto> userDtoList, @Context RoleService roleService, @Context PasswordEncoder passwordEncoder) {
        return userDtoList.stream().map(it -> toEntity(it, roleService, passwordEncoder)).toList();
    }


}
