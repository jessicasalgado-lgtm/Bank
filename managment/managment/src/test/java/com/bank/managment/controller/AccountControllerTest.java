package com.bank.managment.controller;

import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAccountById_success() throws Exception {
        AccountDTO dto = new AccountDTO(1L, "12345", "Ahorros", 1000.0, 1L);
        when(accountService.getById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("12345"));
    }

    @Test
    void testGetAccountById_notFound() throws Exception {
        when(accountService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/accounts/99"))
                .andExpect(status().isNotFound());
    }
}
