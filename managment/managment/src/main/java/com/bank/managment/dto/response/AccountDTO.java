package com.bank.managment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long idAccount;
    private String accountNumber;
    private String accountType;
    private Double balance;
}
