package com.example.demo.models;

import com.example.demo.models.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated
    private RoleType roleType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private LibrarySystemUser user;
    @OneToMany
    @JoinColumn(name = "authority_id")
    private Set<Authority> authorities;

    public Role(RoleType roleType){
        this.roleType = roleType;
    }
}
