package com.example.demo.models;

import com.example.demo.models.enums.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    @Enumerated
    private AuthorityType authorityType;

    public Authority(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }
}
