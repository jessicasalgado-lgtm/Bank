package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.request.UpdateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.entity.User;
import com.bank.managment.exception.DuplicateException;
import com.bank.managment.exception.NotFoundException;
import com.bank.managment.mapper.AccountMapper;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.repository.UserRepository;
import com.bank.managment.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDTO save(CreateAccountDTO dto) {

        if (accountRepository.existsByAccountNumber(dto.getAccountNumber())) {
            throw new DuplicateException("Ya existe una cuenta con el número " + dto.getAccountNumber());
        }

        User user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + dto.getIdUser()));

        Account account = accountMapper.toEntity(dto);
        account.setUser(user);

        Account saved = accountRepository.save(account);

        return accountMapper.toDTO(saved);
    }


    @Override
    public AccountDTO update(UpdateAccountDTO dto) {
        return accountRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setAccountNumber(dto.getAccountNumber());
                    existing.setAccountType(dto.getAccountType());
                    existing.setBalance(dto.getBalance());
                    return accountMapper.toDTO(accountRepository.save(existing));
                })
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + dto.getId()));
    }

    @Override
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new NotFoundException("Cuenta no encontrada con id " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<AccountDTO> getById(Long id) {
        return Optional.ofNullable(accountRepository.findById(id)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + id)));
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }
}


























/*package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.request.UpdateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.entity.User;
import com.bank.managment.mapper.AccountMapper;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.repository.UserRepository;
import com.bank.managment.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
    }
    @Override
    public AccountDTO save(CreateAccountDTO dto) {
        User user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + dto.getIdUser()));

        Account account = accountMapper.toEntity(dto);
        account.setUser(user); // ✅ enlazar usuario

        Account saved = accountRepository.save(account);
        return accountMapper.toDTO(saved);
    }


    @Override
    public AccountDTO update(UpdateAccountDTO dto) {
        return accountRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setAccountNumber(dto.getAccountNumber());
                    existing.setAccountType(dto.getAccountType());
                    existing.setBalance(dto.getBalance());
                    return accountMapper.toDTO(accountRepository.save(existing));
                })
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + dto.getId()));
    }

    @Override
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada con id " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<AccountDTO> getById(Long id) {
        return accountRepository.findById(id).map(accountMapper::toDTO);
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }


}*/



