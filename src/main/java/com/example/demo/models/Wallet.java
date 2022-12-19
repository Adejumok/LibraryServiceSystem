package com.example.demo.models;

import com.example.demo.models.enums.WalletType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated
    private WalletType walletType;
    private Date date;
    private String description;
}
