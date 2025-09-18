package com.bank.managment.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity


public class User {
    private Long idUser;
    private String name;
    private String email;
    private String password;

    // Un usuario puede tener varias cuentas
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;
}
