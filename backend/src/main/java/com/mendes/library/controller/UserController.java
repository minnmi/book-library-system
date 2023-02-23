package com.mendes.library.controller;

import com.mendes.library.model.DTO.UserDTO.UserDTO;
import com.mendes.library.model.User;
import com.mendes.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PreAuthorize("hasAnyAuthority('USER_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public List<UserDTO> findAllUsers() {
        return this.userService
                .findAllUser()
                .stream()
                .map(userService::convertEntityToDto)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('USER_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public UserDTO findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return userService.convertEntityToDto(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO insertUser(@Valid @RequestBody UserDTO objectDTO) {
        User user = userService.convertDtoToEntity(objectDTO);
        user = userService.insertUser(user);
        return userService.convertEntityToDto(user);
    }


    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@Valid @RequestBody UserDTO objectDTO, @PathVariable Long id) {
        User user = userService.convertDtoToEntity(objectDTO);
        user = userService.updateUser(id, user);
        return userService.convertEntityToDto(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        if (!Objects.isNull(id))
            userService.deleteUser(id);
    }


}
