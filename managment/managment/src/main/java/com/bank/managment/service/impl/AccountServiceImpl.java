package com.bank.managment.service.impl;

import com.bank.managment.entity.Account;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Long id, Account accountDetails) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setAccountNumber(accountDetails.getAccountNumber());
                    account.setAccountType(accountDetails.getAccountType());
                    account.setBalance(accountDetails.getBalance());
                    account.setUser(accountDetails.getUser());
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + id));
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }
}

