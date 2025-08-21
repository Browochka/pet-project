package com.browka.bankinglearning.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Entity
@Table(name = "banking")
public class BankUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private int balance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private roles Role;
    public enum roles {
        USER,ADMIN
    }
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "bankUser")
    private Mail mail;
}

