package com.mendes.library.controller;

import com.mendes.library.model.DTO.UserDTO.UserDTO;
import com.mendes.library.model.User;
import com.mendes.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/find/all")
    public List<UserDTO> findAllUsers() {
        var f = SecurityContextHolder.getContext().getAuthentication();
        return this.userService
                .findAllUser()
                .stream()
                .map(userService::convertEntityToDto)
                .toList();
    }

    @GetMapping("/find/{id}")
    public UserDTO findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return userService.convertEntityToDto(user);
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO insertUser(@Valid @RequestBody UserDTO objectDTO) {
        User user = userService.convertDtoToEntity(objectDTO);
        user = userService.insertUser(user);
        return userService.convertEntityToDto(user);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@Valid @RequestBody UserDTO objectDTO, @PathVariable Long id) {
        User user = userService.convertDtoToEntity(objectDTO);
        user = userService.updateUser(id, user);
        return userService.convertEntityToDto(user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        if (!Objects.isNull(id))
            userService.deleteUser(id);
    }
}
