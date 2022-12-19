package com.example.demo.service;

import com.example.demo.dto.request.RegisterNewUserRequest;
import com.example.demo.dto.response.RegisterNewUserResponse;
import com.example.demo.exception.LibrarySystemException;
import com.example.demo.models.Authority;
import com.example.demo.models.LibrarySystemUser;
import com.example.demo.models.Role;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LibrarySystemUserServiceImpl implements LibrarySystemUserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LibrarySystemUser user = userRepository.findByEmail(username).orElse(null);
        if (user != null){
            return new User(user.getEmail(),user.getPassword(),getRoles(user.getRoles()));
        }
        throw new LibrarySystemException(username+" email doesn't exist", 404);
    }

    private Collection<? extends GrantedAuthority> getRoles(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(
                role.getAuthorities().toString())).collect(Collectors.toSet());
    }

    @Override
    public RegisterNewUserResponse registerNewUserResponse(RegisterNewUserRequest registerNewUserRequest) {
        Optional<LibrarySystemUser> foundUser = userRepository.findByEmail(registerNewUserRequest.getEmail());
        if (foundUser.isPresent()){
            throw new  LibrarySystemException("Library user with email "+registerNewUserRequest.getEmail()+" " +
                    "already registered", 400);
        }
        LibrarySystemUser user = mapper.map(registerNewUserRequest, LibrarySystemUser.class);
        String encodedUserPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedUserPassword);
        LibrarySystemUser registeredUser = userRepository.save(user);
        RegisterNewUserResponse response = new RegisterNewUserResponse();
        response.setMessage(registeredUser.getFirstName()+" successfully registered");
        response.setEmail(registeredUser.getEmail());
        return response;
    }
}
