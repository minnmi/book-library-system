package com.mendes.library.config.security.user;


import com.mendes.library.config.security.user.CustomUserDetail;
import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailService() {
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
        return new CustomUserDetail(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

}
