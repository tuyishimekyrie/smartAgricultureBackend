package com.sensor.agri.smartagriculturebackend.security;

import com.sensor.agri.smartagriculturebackend.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final PasswordEncoder passwordEncoder;
        private String username;
    private String password;
    private String email; // Custom field
//    private Collection<? extends GrantedAuthority> authorities;

//    public CustomUserDetails(String username, String password, String email,
//                             Collection<? extends GrantedAuthority> authorities) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.authorities = authorities;
//    }

    public String getEmail() {
        return email;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user.getEmail();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
