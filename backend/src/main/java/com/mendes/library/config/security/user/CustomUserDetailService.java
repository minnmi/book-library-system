package com.mendes.library.config.security.user;


import com.mendes.library.model.Authority;
import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty())
            throw new UsernameNotFoundException(username);

        User user = userOpt.get();

        var f= new CustomUserDetail(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .parallelStream()
                        .flatMap(u -> u.getAuthorities().parallelStream())
                        .map(Authority::getName)
                        .collect(Collectors.toSet()));

        return f;
    }

}
