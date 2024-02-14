package by.teachmeskills.lesson41.controller.api;


import by.teachmeskills.lesson41.dto.RegisterUserDto;
import by.teachmeskills.lesson41.dto.UserDto;
import by.teachmeskills.lesson41.jpa.OffsetLimitPageable;
import by.teachmeskills.lesson41.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Управление пользователями")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('MANAGER','LIBRARIAN')")
    @GetMapping
    @Operation(operationId = "Получить всех", description = "Получить всех пользователей")
    public List<UserDto> findAll(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Long offset) {
        return userService.getAll(OffsetLimitPageable.of(offset < 0 ? 0L : offset, limit > 10 ? 10 : limit, Sort.by(Sort.Direction.ASC, "id")));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','LIBRARIAN')")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable("userId") Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @SneakyThrows
    @PostMapping(value = "/regmng")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<UserDto> createWithRole(@RequestBody @Valid RegisterUserDto registerUserDto) {

        if (registerUserDto.getId() != null && registerUserDto.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.addUserWithRole(registerUserDto));
    }

    @SneakyThrows
    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER','LIBRARIAN')")
    public ResponseEntity<UserDto> createDefault(@RequestBody @Valid RegisterUserDto registerUserDto) {
        log.info("user: {}", registerUserDto);
        if (registerUserDto.getId() != null && registerUserDto.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.addUserDefault(registerUserDto));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','LIBRARIAN')")
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody @Valid RegisterUserDto registerUserDto) {
        userService.editUser(registerUserDto.setId(id));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','LIBRARIAN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }


}
