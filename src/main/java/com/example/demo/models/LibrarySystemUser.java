package com.example.demo.models;

import com.example.demo.models.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibrarySystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles;
    private String mobile;
    private String address;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Wallet> wallets;

    public LibrarySystemUser(String firstName, String lastName, String email, String password, RoleType roleType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        if (roles == null){
            roles = new HashSet<>();
            roles.add(new Role(roleType));
        }
    }
}
