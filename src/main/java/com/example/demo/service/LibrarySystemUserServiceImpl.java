package com.example.demo.service;

import com.example.demo.dto.request.MailRequest;
import com.example.demo.dto.request.RegisterNewUserRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.RegisterNewUserResponse;
import com.example.demo.dto.response.UserUpdateResponse;
import com.example.demo.exception.LibrarySystemException;
import com.example.demo.models.LibrarySystemUser;
import com.example.demo.models.Role;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.notification.EmailService;
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
    private final EmailService emailService;
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
        sendMail(registerNewUserRequest);
        return RegisterNewUserResponse.builder()
                .message(registeredUser.getFirstName()+" registered successfully.")
                .email(registeredUser.getEmail())
                .build();
    }

    @Override
    public UserUpdateResponse userUpdateResponse(UserUpdateRequest updateRequest) {
        Optional<LibrarySystemUser> foundUser = userRepository.findByEmail(updateRequest.getEmail());
        if (foundUser.isPresent()){
            LibrarySystemUser existingUser = foundUser.get();
            existingUser.setLastName(updateRequest.getLastName());
            existingUser.setAddress(updateRequest.getAddress());
            existingUser.setMobile(updateRequest.getMobile());
            userRepository.save(existingUser);
            return UserUpdateResponse.builder()
                    .message(existingUser.getFirstName()+" profile updated successfully.")
                    .build();
        }
        throw new  LibrarySystemException("Library user with email "+updateRequest.getEmail()+" " +
                "does not exist.", 400);
    }

    @Override
    public LibrarySystemUser getUserByEmail(String email) {
        LibrarySystemUser foundUser = userRepository.findByEmail(email).orElse(null);
        if (foundUser != null){
            return foundUser;
        }
        throw new LibrarySystemException("User with "+email+" not found", 404);
    }

    private void sendMail(RegisterNewUserRequest request){
        MailRequest.builder()
                .sender(System.getenv("SENDER"))
                .receiver(request.getEmail())
                .subject("Library System")
                .body("Hello " + request.getFirstName() + ". " +
                        "We are glad to let you know you have successfully registered")
                .build();

    }


}
