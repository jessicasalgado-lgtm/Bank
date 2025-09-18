package com.bank.managment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {
    private Long idAccount;
    private String accountNumber;
    private String accountType;
    private Double balance;
}
