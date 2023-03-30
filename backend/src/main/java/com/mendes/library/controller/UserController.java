package com.mendes.library.controller;

import com.mendes.library.model.DTO.UserDTO.*;
import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
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
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;

        this.userRepository = userRepository;
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
        var user = this.userService.findById(id);
        return userService.convertEntityToDto(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_VIEW', 'ADMIN')")
    @GetMapping("/find-loans/user/{id}")
    public List<UserLoansBooksDTO> findLoansByUserId(@PathVariable Long id) {
        return this.userService.findLoansByUserId(id)
                .stream()
                .map(userService::convertEntityToUserLoansBooksDTO)
                .toList();
    }


    @PreAuthorize("hasAnyAuthority('USER_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse insertUser(@Valid @RequestBody UserRequest userRequest) {
        var userEntity = userService.convertDtoToEntity(userRequest);
        var user = userService.insertUser(userEntity);
        return userService.convertEntityToDto(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_INSERT', 'ADMIN')")
    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleName") String roleName) {
        var user = this.userService.findById(userId);
        this.userService.addRoleToUser(user, roleName);
    }

    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'ADMIN')")
    @PutMapping("/email/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserUpdateEmailDTO updateUserEmail(@Valid @RequestBody UserUpdateEmailDTO userUpdateEmailDTO, @PathVariable Long id) {
        var userEntity = userService.convertUserUpdateEmailDTOToEntity(userUpdateEmailDTO);
        var user = userService.emailUpdate(id, userEntity);
        return userService.convertEntityToUserUpdateEmailDTO(user);
    }


    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id) {
        var userEntity = userService.convertDtoToEntity(userRequest);
        var user = userService.updateUser(id, userEntity);
        return userService.convertEntityToDto(user);
    }

    @PostMapping("/update-password")
    public void updatePassword(@Valid @RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO) {
        var user = userService.findByEmail(userUpdatePasswordDTO.getEmail());
        this.userService.updateUserPassword(user,
                userUpdatePasswordDTO.getNewpassword(),
                userUpdatePasswordDTO.getConfirmpassword(),
                userUpdatePasswordDTO.getOldpassword());
    }

    @PreAuthorize("hasAnyAuthority('USER_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
    }


}
