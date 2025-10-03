package com.bank.managment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;
    private String accountNumber;
    private String accountType;
    private Double balance;
    private Long idUser;

    // Muchas cuentas pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // Una cuenta puede tener muchas transacciones
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;



}
