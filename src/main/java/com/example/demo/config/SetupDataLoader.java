package com.example.demo.config;

import com.example.demo.models.LibrarySystemUser;
import com.example.demo.models.enums.AuthorityType;
import com.example.demo.models.enums.RoleType;
import com.example.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepository.findByEmail("adminuser@gmail.com").isEmpty()){
            LibrarySystemUser user = new LibrarySystemUser("Admin",
                    "User","adminuser@gmail.com", passwordEncoder.encode("password1234#"), RoleType.ROLE_ADMIN);
            userRepository.save(user);
        }
    }
}
