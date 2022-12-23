package com.mendes.library.security;


import com.mendes.library.model.User;
import com.mendes.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailService() {
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        List<User> userd = userRepository.findAll();
        Optional<User> userOpt = this.userRepository.findByEmail(email);
        if (userOpt.isEmpty())
            throw new UsernameNotFoundException(email);

        User user = userOpt.get();
        return new CustomUserDetail(user.getEmail(), user.getPassword());
    }

}
