package com.bank.managment.service;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.entity.Transaction;
import com.bank.managment.exception.NotFoundException;
import com.bank.managment.exception.TransactionNotValidException;
import com.bank.managment.mapper.TransactionMapper;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.repository.TransactionRepository;
import com.bank.managment.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;
    private AccountRepository accountRepository;
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionMapper = mock(TransactionMapper.class);
        accountRepository = mock(AccountRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository, transactionMapper, accountRepository);
    }

    @Test
    void testSaveTransaction_credit_success() {
        CreateTransactionDTO dto = new CreateTransactionDTO("CREDIT", 500.0, 1L);

        Account account = new Account();
        account.setIdAccount(1L);
        account.setBalance(1000.0);

        Transaction entity = new Transaction();
        entity.setAmount(500.0);
        entity.setTransactionType("CREDIT");
        entity.setAccount(account);

        Transaction saved = new Transaction();
        saved.setIdTransaction(10L);
        saved.setAmount(500.0);
        saved.setTransactionType("CREDIT");
        saved.setAccount(account);

        TransactionDTO expected = new TransactionDTO(10L, "CREDIT", 500.0, 1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionMapper.toEntity(dto)).thenReturn(entity);
        when(transactionRepository.save(entity)).thenReturn(saved);
        when(transactionMapper.toDTO(saved)).thenReturn(expected);

        TransactionDTO result = transactionService.save(dto);

        assertNotNull(result);
        assertEquals(10L, result.getIdTransaction());
        assertEquals("CREDIT", result.getTransactionType());
    }

    @Test
    void testSaveTransaction_debit_insufficientFunds_throwsException() {
        CreateTransactionDTO dto = new CreateTransactionDTO("DEBIT", 2000.0, 1L);

        Account account = new Account();
        account.setIdAccount(1L);
        account.setBalance(1000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(TransactionNotValidException.class, () -> transactionService.save(dto));
    }

    @Test
    void testSaveTransaction_invalidAmount_throwsException() {
        CreateTransactionDTO dto = new CreateTransactionDTO("CREDIT", -50.0, 1L);

        Account account = new Account();
        account.setIdAccount(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(TransactionNotValidException.class, () -> transactionService.save(dto));
    }

    @Test
    void testSaveTransaction_accountNotFound() {
        CreateTransactionDTO dto = new CreateTransactionDTO("CREDIT", 100.0, 99L);

        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.save(dto));
    }
}
