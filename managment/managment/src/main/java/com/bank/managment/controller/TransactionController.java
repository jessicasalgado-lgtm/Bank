package com.bank.managment.controller;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.entity.Transaction;
import com.bank.managment.mapper.TransactionMapper;
import com.bank.managment.service.AccountService;
import com.bank.managment.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService,
                                 AccountService accountService,
                                 TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
    }

    // ✅ GET ALL
    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAll()
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return transactionService.getById(id)
                .map(transaction -> ResponseEntity.ok(transactionMapper.toDTO(transaction)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody CreateTransactionDTO dto) {
        Account account = accountService.getById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + dto.getAccountId()));

        Transaction transaction = transactionMapper.toEntity(dto);
        transaction.setAccount(account);

        // actualizar saldo automáticamente
        if ("DEPOSIT".equalsIgnoreCase(transaction.getTransactionType())) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else if ("WITHDRAW".equalsIgnoreCase(transaction.getTransactionType())) {
            if (account.getBalance() < transaction.getAmount()) {
                throw new RuntimeException("Saldo insuficiente");
            }
            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        Transaction saved = transactionService.save(transaction);
        return ResponseEntity.ok(transactionMapper.toDTO(saved));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(
            @PathVariable Long id,
            @RequestBody CreateTransactionDTO dto) {
        Account account = accountService.getById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + dto.getAccountId()));

        Transaction transaction = transactionMapper.toEntity(dto);
        transaction.setAccount(account);

        Transaction updated = transactionService.update(id, transaction);
        return ResponseEntity.ok(transactionMapper.toDTO(updated));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
