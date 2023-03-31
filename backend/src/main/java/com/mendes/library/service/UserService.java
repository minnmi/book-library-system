package com.mendes.library.service;

import com.mendes.library.config.security.user.CustomUserDetail;
import com.mendes.library.model.DTO.UserDTO.*;
import com.mendes.library.model.Role;
import com.mendes.library.model.User;
import com.mendes.library.repository.RoleRepository;
import com.mendes.library.repository.UserRepository;
import com.mendes.library.service.exception.AuthorizationException;
import com.mendes.library.service.exception.DataIntegrityViolationException;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Page<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    public List<UserLoansBooksDTO> findLoansByUserId(Long userId) {
        return this.userRepository.findLoansByUserId(userId);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found: " + id + " type " + User.class.getName()));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Object not found: " + email + " type " + User.class.getName()));
    }

    public User insertUser(User user) {
        verifyUser(user.getUsername(), user.getEmail());
        user.setId(null);
        user.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null.");
        }

        User currentUser = findById(id);
        verifyUserUsername(currentUser, user);
        toUpdateUser(currentUser, user);
        return userRepository.save(currentUser);
    }

    private void toUpdateUser(User currentUser, User user) {
        currentUser.setName(user.getName());
        currentUser.setUsername(user.getUsername());
    }

    public void addRoleToUser(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public User emailUpdate(Long id, User user) {
        User currentUser = findById(id);
        verifyUserEmail(currentUser, user);
        currentUser.setEmail(user.getEmail());
        return userRepository.save(currentUser);
    }

    public void updateUserPassword(User user, String newPassword, String confirmPassword, String oldPassword) {

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AuthorizationException("OldPassword invalid");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new AuthorizationException("NewPassword and ConfirmPassword are different");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private void verifyUserUsername(User currentUser, User newUser) {
        if (Objects.equals(currentUser.getUsername(), newUser.getUsername())) {
            throw new DataIntegrityViolationException("Username already exist");
        }
    }

    private void verifyUserEmail(User newUser, User oldUser) {
        if (Objects.equals(newUser.getEmail(), oldUser.getEmail())) {
            throw new DataIntegrityViolationException("Email already exist");
        }
    }

    private void verifyUser(String username, String email) {
        Optional<User> usernameVerify = userRepository.findByUsername(username);
        Optional<User> emailVerify = userRepository.findByEmail(email);

        if (usernameVerify.isPresent()) {
            throw new DataIntegrityViolationException("Username already exist");
        }

        if (emailVerify.isPresent()) {
            throw new DataIntegrityViolationException("Email already exist");
        }

    }
    public User getLoggedUser() {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return this.findById(userDetail.getId());
    }

    public void deleteUser(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        this.userRepository.deleteById(id);
    }


    public User convertDtoToEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public UserResponse convertEntityToDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User convertUserUpdateEmailDTOToEntity(UserUpdateEmailDTO userUpdateEmailDTO) {
        return modelMapper.map(userUpdateEmailDTO, User.class);
    }

    public UserUpdateEmailDTO convertEntityToUserUpdateEmailDTO(User user) {
        return modelMapper.map(user, UserUpdateEmailDTO.class);
    }

}
