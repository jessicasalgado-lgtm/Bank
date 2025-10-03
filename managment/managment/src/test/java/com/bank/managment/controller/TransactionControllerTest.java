package com.bank.managment.controller;


import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.service.TransactionService;
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

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetTransactionById_success() throws Exception {
        TransactionDTO dto = new TransactionDTO(1L, "DEPOSIT", 500.0, 1L);
        when(transactionService.getById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("DEPOSIT"));
    }

    @Test
    void testGetTransactionById_notFound() throws Exception {
        when(transactionService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/transactions/99"))
                .andExpect(status().isNotFound());
    }
}
