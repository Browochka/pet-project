package com.browka.bankinglearning.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="emails")
public class Mail {
    @Id
    private int id;
    private String email;
    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private BankUser bankUser;

}
