package com.bank.managment.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaction {
    private Long idTransaction;
    private Double amount;
    private String transactionType;

    // Muchas transacciones pertenecen a una cuenta
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
