package com.bank.managment.service;

import com.bank.managment.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Account update(Long id, Account accountDetails);
    void delete(Long id);
    Optional<Account> getById(Long id);
    List<Account> getAll();
}

