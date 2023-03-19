package com.mendes.library.controller;

import com.mendes.library.model.DTO.UserDTO.UserRequest;
import com.mendes.library.model.DTO.UserDTO.UserResponse;
import com.mendes.library.model.DTO.UserDTO.UserRoles;
import com.mendes.library.model.DTO.UserDTO.UserUpdateEmail;
import com.mendes.library.model.User;
import com.mendes.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PreAuthorize("hasAnyAuthority('USER_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<UserResponse> findAllUsers(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var page = this.userService.findAllUser(pageable);

        var content = page.getContent()
                .stream()
                .map(userService::convertEntityToDto)
                .toList();

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('USER_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public UserResponse findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return userService.convertEntityToDto(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse insertUser(@Valid @RequestBody UserRequest userRequest) {
        User userEntity = userService.convertDtoToEntity(userRequest);
        var user = userService.insertUser(userEntity);
        return userService.convertEntityToDto(user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoles insertUser(@Valid @RequestBody UserRoles userRoles) {
        User userEntity = userService.convertUserRolesToEntity(userRoles);
        var user = userService.insertRole(userEntity);
        return userService.convertEntityToUserRoles(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'ADMIN')")
    @PutMapping("/email/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserUpdateEmail updateUser(@Valid @RequestBody UserUpdateEmail userUpdateEmail, @PathVariable Long id) {
        User userEntity = userService.convertUserUpdateEmailToEntity(userUpdateEmail);
        var user = userService.emailUpdate(id, userEntity);
        return userService.convertEntityToUserUpdateEmail(user);
    }


    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id) {
        User userEntity = userService.convertDtoToEntity(userRequest);
        var user = userService.updateUser(id, userEntity);
        return userService.convertEntityToDto(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
    }


}
