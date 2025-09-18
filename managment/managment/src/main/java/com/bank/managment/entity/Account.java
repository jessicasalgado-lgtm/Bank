package com.bank.managment.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // Muchas cuentas pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "user_id") // nombre de la columna FK en la tabla Account
    private User user;
}
