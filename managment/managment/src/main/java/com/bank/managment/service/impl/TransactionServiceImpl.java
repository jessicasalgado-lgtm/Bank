package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.entity.Transaction;
import com.bank.managment.mapper.TransactionMapper;
import com.bank.managment.repository.TransactionRepository;
import com.bank.managment.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO save(CreateTransactionDTO dto) {
        Transaction transaction = transactionMapper.toEntity(dto);
        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDTO update(Long id, CreateTransactionDTO dto) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setTransactionType(dto.getTransactionType());
                    transaction.setAmount(dto.getAmount());
                    return transactionMapper.toDTO(transactionRepository.save(transaction));
                })
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con id " + id));
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Optional<TransactionDTO> getById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toDTO);
    }

    @Override
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }
}

