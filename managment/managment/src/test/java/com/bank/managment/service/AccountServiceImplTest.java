package com.bank.managment.service;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.entity.User;
import com.bank.managment.exception.NotFoundException;
import com.bank.managment.mapper.AccountMapper;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.repository.UserRepository;
import com.bank.managment.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private UserRepository userRepository;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountMapper = mock(AccountMapper.class);
        userRepository = mock(UserRepository.class);
        accountService = new AccountServiceImpl(accountRepository, accountMapper, userRepository);
    }

    @Test
    void testSaveAccount_success() {
        CreateAccountDTO dto = new CreateAccountDTO("12345", "Ahorros", 1000.0, 1L);

        User user = new User();
        user.setIdUser(1L);

        Account accountEntity = new Account();
        accountEntity.setAccountNumber("12345");
        accountEntity.setBalance(1000.0);

        Account savedEntity = new Account();
        savedEntity.setIdAccount(10L);
        savedEntity.setAccountNumber("12345");
        savedEntity.setBalance(1000.0);
        savedEntity.setUser(user);

        AccountDTO expected = new AccountDTO(10L, "12345", "Ahorros", 1000.0, 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountMapper.toEntity(dto)).thenReturn(accountEntity);
        when(accountRepository.save(accountEntity)).thenReturn(savedEntity);
        when(accountMapper.toDTO(savedEntity)).thenReturn(expected);

        AccountDTO result = accountService.save(dto);

        assertNotNull(result);
        assertEquals("12345", result.getAccountNumber());
        assertEquals(1000.0, result.getBalance());
    }

    @Test
    void testSaveAccount_userNotFound_throwsException() {
        CreateAccountDTO dto = new CreateAccountDTO("12345", "Ahorros", 1000.0, 99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.save(dto));
    }

    @Test
    void testGetById_notFound() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getById(99L));
    }

    @Test
    void testDelete_success() {
        when(accountRepository.existsById(1L)).thenReturn(true);

        accountService.delete(1L);

        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_notFound() {
        when(accountRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> accountService.delete(99L));
    }

    @Test
    void testGetAll_success() {
        Account account = new Account();
        account.setIdAccount(1L);
        account.setAccountNumber("12345");
        account.setBalance(500.0);

        AccountDTO dto = new AccountDTO(1L, "12345", "Ahorros", 500.0, 1L);

        when(accountRepository.findAll()).thenReturn(List.of(account));
        when(accountMapper.toDTO(account)).thenReturn(dto);

        List<AccountDTO> result = accountService.getAll();

        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).getAccountNumber());
    }
}
