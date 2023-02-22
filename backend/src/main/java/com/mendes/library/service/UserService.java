package com.mendes.library.service;

import com.mendes.library.config.security.user.CustomUserDetail;
import com.mendes.library.model.DTO.UserDTO.UserDTO;
import com.mendes.library.model.DTO.UserDTO.UserUpdateDTO;
import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import com.mendes.library.service.exception.BusinessException;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + User.class.getName());
        }
    }

    public User insertUser(User object) {
        object.setId(null);
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return userRepository.save(object);
    }

    public User updateUser(Long id, User object) {
        if (object == null || object.getId() == null) {
            throw new IllegalArgumentException("User can't be null.");
        }
        User newObject = findById(id);
        toUpdateUser(newObject, object);
        verifyUpdateUser(newObject, object);
        return userRepository.save(newObject);
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
     * @param newObject
     * @param object
     */

    private void toUpdateUser(User newObject, User object) {
        newObject.setName(object.getName());
        newObject.setEmail(object.getEmail());
        newObject.setPassword(object.getPassword());
    }

    public User convertDtoToEntity(UserDTO objectDTO) {
        return modelMapper.map(objectDTO, User.class);
    }

    public UserDTO convertEntityToDto(User object) {
        return modelMapper.map(object, UserDTO.class);
    }

    public User convertUpdateDtoToEntity(UserUpdateDTO objectDTO) {
        return modelMapper.map(objectDTO, User.class);
    }

    public UserUpdateDTO convertEntityToUpdateDto(User object) {
        return modelMapper.map(object, UserUpdateDTO.class);
    }

}
