package com.mendes.library.service;

import com.mendes.library.config.security.user.CustomUserDetail;
import com.mendes.library.model.DTO.UserDTO.*;
import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import com.mendes.library.service.exception.DataIntegrityViolationException;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Page<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + User.class.getName());
        }
    }

    public User insertUser(User user) {
        verifyUser(user.getUsername(), user.getEmail());
        user.setId(null);
        user.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }


    public User updateUser(Long id, User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User can't be null.");
        }

        User currentUser = findById(id);
        verifyUserUsername(currentUser, user);
        toUpdateUser(currentUser, user);
        return userRepository.save(currentUser);
    }

    public void deleteUser(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        this.userRepository.deleteById(id);
    }

    public User insertRole(User user) {
        //TODO endpoint apenas com preauthorize de ADMIN
        user.setRoles(user.getRoles());
        return userRepository.save(user);
    }

    public User emailUpdate(Long id, User user) {
        User currentUser = findById(id);
        verifyUserEmail(currentUser, user);
        user.setEmail(user.getEmail());
        return userRepository.save(currentUser);
    }

//    public boolean updateUserPassword(User user, String newPassword, String confirmPassword, String oldPassword) {
//
//        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
//            throw new AuthorizationException("OldPassword invalid");
//        }
//
//        if (!newPassword.equals(confirmPassword)) {
//            throw new AuthorizationException("NewPassword and ConfirmPassword are different");
//        }
//
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//        return true;
//    }

    private void verifyUserUsername(User newUser, User oldUser) {
        User usernameVerify = userRepository.findByUsername(newUser.getUsername()).get();
        if (!Objects.equals(usernameVerify.getUsername(), oldUser.getUsername())) {
            throw new DataIntegrityViolationException("Username already exist");
        }
    }

    private void verifyUserEmail(User newUser, User oldUser) {
        User emailVerify = userRepository.findByEmail(newUser.getEmail()).get();
        if (!Objects.equals(emailVerify.getEmail(), oldUser.getEmail())) {
            throw new DataIntegrityViolationException("Email already exist");
        }
    }

    private void verifyUser(String username, String email) {
        Optional<User> usernameVerify = userRepository.findByUsername(username);
        Optional<User> emailVerify = userRepository.findByEmail(email);
        if (usernameVerify.isPresent()) {
            throw new org.springframework.dao.DataIntegrityViolationException("Username already exist");
        }

        if (emailVerify.isPresent()) {
            throw new org.springframework.dao.DataIntegrityViolationException("Email already exist");
        }

    }

    public User getLoggedUser() {
        CustomUserDetail userDetail = (CustomUserDetail)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return this.findById(userDetail.getId());
    }

    /**
     * Update object with new informations
     *
     * @param currentUser
     * @param user
     */

    private void toUpdateUser(User currentUser, User user) {
        currentUser.setName(user.getName());
        currentUser.setUsername(user.getUsername());
    }

    public User convertDtoToEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public UserResponse convertEntityToDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User convertUserUpdateToEntity(UserUpdate userUpdate) {
        return modelMapper.map(userUpdate, User.class);
    }

    public UserUpdate convertEntityToUserUpdate(User user) {
        return modelMapper.map(user, UserUpdate.class);
    }

    public User convertUserUpdateEmailToEntity(UserUpdateEmail userUpdateEmail) {
        return modelMapper.map(userUpdateEmail, User.class);
    }

    public UserUpdateEmail convertEntityToUserUpdateEmail(User user) {
        return modelMapper.map(user, UserUpdateEmail.class);
    }

    public User convertUserRolesToEntity(UserRoles userRoles)  {
        return modelMapper.map(userRoles, User.class);
    }

    public UserRoles convertEntityToUserRoles(User user) {
        return modelMapper.map(user, UserRoles.class);
    }

}
