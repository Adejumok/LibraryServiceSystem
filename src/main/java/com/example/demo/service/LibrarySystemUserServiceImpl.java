package com.example.demo.service;

import com.example.demo.dto.request.userRequest.MailRequest;
import com.example.demo.dto.request.userRequest.RegisterNewUserRequest;
import com.example.demo.dto.request.userRequest.UserUpdateRequest;
import com.example.demo.dto.response.userResponse.RegisterNewUserResponse;
import com.example.demo.dto.response.userResponse.UserUpdateResponse;
import com.example.demo.exception.LibrarySystemException;
import com.example.demo.models.LibrarySystemUser;
import com.example.demo.models.Role;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.notification.EmailService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
 class LibrarySystemUserServiceImpl implements LibrarySystemUserService {
    private final UserRepository userRepository;
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
        if (foundUser.isEmpty()){
            LibrarySystemUser user = new LibrarySystemUser();
                    registerNewUser(registerNewUserRequest, user);
            return RegisterNewUserResponse.builder()
                    .message(user.getFirstName()+" registered successfully.")
                    .email(user.getEmail())
                    .build();
        }
        
        throw new  LibrarySystemException("Library user with email "+registerNewUserRequest.getEmail()+" " +
                "already registered", 400);
    }

    private LibrarySystemUser registerNewUser(RegisterNewUserRequest registerNewUserRequest, LibrarySystemUser user ) {
        user.setFirstName(registerNewUserRequest.getFirstName());
        user.setEmail(registerNewUserRequest.getEmail());
        String encodedUserPassword = bCryptPasswordEncoder.encode(registerNewUserRequest.getPassword());
        user.setPassword(encodedUserPassword);
        return userRepository.save(user);
    }

    @Override
    public UserUpdateResponse userUpdateResponse(UserUpdateRequest updateRequest) {
        Optional<LibrarySystemUser> foundUser = userRepository.findByEmail(updateRequest.getEmail());
        if (foundUser.isPresent()){
            LibrarySystemUser existingUser = foundUser.get();
            setExistingUserRemainingDetailsToUpdateRequest(updateRequest, existingUser);
            userRepository.save(existingUser);
            return UserUpdateResponse.builder()
                    .message(existingUser.getFirstName()+" profile updated successfully.")
                    .build();
        }
        throw new  LibrarySystemException("Library user with email "+updateRequest.getEmail()+" " +
                "does not exist.", 400);
    }

    private void setExistingUserRemainingDetailsToUpdateRequest(UserUpdateRequest updateRequest, LibrarySystemUser existingUser) {
        existingUser.setLastName(updateRequest.getLastName());
        existingUser.setAddress(updateRequest.getAddress());
        existingUser.setMobile(updateRequest.getMobile());
    }

    @Override
    public LibrarySystemUser getUserByEmail(String email) {
        LibrarySystemUser foundUser = userRepository.findByEmail(email).orElse(null);
        if (foundUser != null){
            return foundUser;
        }
        throw new LibrarySystemException("User with "+email+" not found", 404);
    }

    @Override
    public List<LibrarySystemUser> getAllUsers() {
        return userRepository.findAll();
    }


    private void sendMail(RegisterNewUserRequest request) throws UnirestException {
        MailRequest mailRequest =MailRequest.builder()
                .sender(System.getenv("SENDER"))
                .receiver(request.getEmail())
                .subject("Library System")
                .body("Hello " + request.getFirstName() + ". " +
                        "We are glad to let you know you have successfully registered")
                .build();
        emailService.sendMail(mailRequest);

    }


}
