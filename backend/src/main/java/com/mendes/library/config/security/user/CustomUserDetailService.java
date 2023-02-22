package com.mendes.library.config.security.user;


import com.mendes.library.model.Authority;
import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("Username: " + username + "not found");
        }));

        if (userOpt.isEmpty())
            throw new UsernameNotFoundException(username);

        User user = userOpt.get();
        return new CustomUserDetail(user.getEmail(), user.getPassword(), user.getRoles().stream()
                .flatMap(u -> u.getAuthorities().stream()).map(Authority::getName).collect(Collectors.toSet()));
    }


}
