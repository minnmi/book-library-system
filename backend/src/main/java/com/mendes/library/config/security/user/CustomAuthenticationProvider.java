package com.mendes.library.config.security.user;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailService;

    public CustomAuthenticationProvider(UserDetailsService customUserDetailService) {
        this.userDetailService = customUserDetailService;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = (String)auth.getName();
        String password = (String)auth.getCredentials();
        UserDetails userDetails = this.userDetailService.loadUserByUsername(email);
        if (password.equals(userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getUsername(),
                    userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Wrong password.");
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
