package com.mendes.library.config.security.user;


import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty())
            throw new UsernameNotFoundException(username);

        User user = userOpt.get();

        return new CustomUserDetail(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities());
    }

}
