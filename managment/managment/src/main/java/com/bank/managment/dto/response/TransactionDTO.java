package com.bank.managment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    private Long idTransaction;
    private String transactionType;
    private Double amount;
    private Long accountId;
}
