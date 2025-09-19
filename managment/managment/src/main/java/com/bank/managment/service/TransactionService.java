package com.bank.managment.service;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionDTO save(CreateTransactionDTO dto);
    TransactionDTO update(Long id, CreateTransactionDTO dto);
    void delete(Long id);
    Optional<TransactionDTO> getById(Long id);
    List<TransactionDTO> getAll();
}
