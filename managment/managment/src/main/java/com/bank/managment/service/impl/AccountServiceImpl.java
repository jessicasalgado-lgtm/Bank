package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.mapper.AccountMapper;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDTO save(CreateAccountDTO dto) {
        Account account = accountMapper.toEntity(dto);
        return accountMapper.toDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO update(Long id, CreateAccountDTO dto) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setAccountNumber(dto.getAccountNumber());
                    account.setAccountType(dto.getAccountType());
                    account.setBalance(dto.getBalance());
                    return accountMapper.toDTO(accountRepository.save(account));
                })
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + id));
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<AccountDTO> getById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDTO);
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }
}


