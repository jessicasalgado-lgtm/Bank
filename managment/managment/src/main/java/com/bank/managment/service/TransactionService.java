package com.bank.managment.service;


import com.bank.managment.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);
    Transaction update(Long id, Transaction transactionDetails);
    void delete(Long id);
    Optional<Transaction> getById(Long id);
    List<Transaction> getAll();
}
