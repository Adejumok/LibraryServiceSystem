package com.example.demo.security;

import com.example.demo.models.Authority;
import com.example.demo.models.LibrarySystemUser;
import com.example.demo.models.Role;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LibrarySysAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        LibrarySystemUser user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found."));

        if (bCryptPasswordEncoder.matches(password, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(email, password, getRoles(user.getRoles()));
        }
        else{
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    private Set<SimpleGrantedAuthority> getRoles(Set<Role> roles) {
        Set<SimpleGrantedAuthority> list = new HashSet<>();
        for (Role role: roles){
            list.add(new SimpleGrantedAuthority(role.getAuthorities().toString()));
        }
        return list;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
