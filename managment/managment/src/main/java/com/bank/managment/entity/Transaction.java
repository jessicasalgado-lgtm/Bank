package com.bank.managment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    private Double amount;
    private String transactionType;

    // Muchas transacciones pertenecen a una cuenta
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
