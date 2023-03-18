package com.mendes.library.service;

import com.mendes.library.config.security.user.CustomUserDetail;
import com.mendes.library.model.DTO.UserDTO.UserRequest;
import com.mendes.library.model.DTO.UserDTO.UserResponse;
import com.mendes.library.model.DTO.UserDTO.UserUpdate;
import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import com.mendes.library.service.exception.BusinessException;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        user.setId(null);
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User can't be null.");
        }
        User currentUser = findById(id);
        toUpdateUser(currentUser, user);
        verifyUpdateUser(currentUser, user);
        return userRepository.save(currentUser);
    }

    public void verifyUpdateUser(User newUser, User oldUser) {
        User emailVerify = userRepository.findByEmail(newUser.getEmail()).get();
        if (emailVerify != null && emailVerify.getEmail() != oldUser.getEmail()) {
            throw new BusinessException("Email already exist");
        }
    }

    public void deleteUser(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        this.userRepository.deleteById(id);
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
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
    }

    public User convertDtoToEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public UserResponse convertEntityToDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User convertUpdateDtoToEntity(UserUpdate userUpdate) {
        return modelMapper.map(userUpdate, User.class);
    }

    public UserUpdate convertEntityToUpdateDto(User user) {
        return modelMapper.map(user, UserUpdate.class);
    }

}
