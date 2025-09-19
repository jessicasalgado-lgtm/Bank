package com.bank.managment.service.impl;

import com.bank.managment.entity.Transaction;
import com.bank.managment.repository.TransactionRepository;
import com.bank.managment.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction update(Long id, Transaction transactionDetails) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setTransactionType(transactionDetails.getTransactionType());
                    transaction.setAmount(transactionDetails.getAmount());
                    transaction.setAccount(transactionDetails.getAccount());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con id " + id));
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Optional<Transaction> getById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }
}

