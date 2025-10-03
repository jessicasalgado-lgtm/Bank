package com.bank.managment.service;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.request.UpdateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionDTO save(CreateTransactionDTO dto);

    void delete(Long id);

    Optional<TransactionDTO> getById(Long id);

    List<TransactionDTO> getAll();

    Object update(@Valid UpdateTransactionDTO transactionDTO);
}
