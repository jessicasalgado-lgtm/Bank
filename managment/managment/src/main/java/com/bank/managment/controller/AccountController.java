package com.bank.managment.controller;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.mapper.AccountMapper;
import com.bank.managment.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    // ✅ GET ALL
    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAll()
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return accountService.getById(id)
                .map(account -> ResponseEntity.ok(accountMapper.toDTO(account)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO dto) {
        Account account = accountMapper.toEntity(dto);
        Account saved = accountService.save(account);
        return ResponseEntity.ok(accountMapper.toDTO(saved));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(
            @PathVariable Long id,
            @RequestBody CreateAccountDTO dto) {
        Account updated = accountService.update(id, accountMapper.toEntity(dto));
        return ResponseEntity.ok(accountMapper.toDTO(updated));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

