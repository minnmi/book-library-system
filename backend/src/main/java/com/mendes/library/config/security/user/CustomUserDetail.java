package com.mendes.library.config.security.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    private String password;
    private String useraname;

    private List<SimpleGrantedAuthority> authorities;

    public CustomUserDetail(String username, String password) {
        this.useraname = username;
        this.password = password;
    }

    public CustomUserDetail(String username, String password, List<String> authorities) {
        this(username, password);
        this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.useraname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
