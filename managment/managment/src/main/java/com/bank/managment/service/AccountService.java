package com.bank.managment.service;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.request.UpdateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    AccountDTO save(CreateAccountDTO dto);

    void delete(Long id);

    Optional<AccountDTO> getById(Long id);

    List<AccountDTO> getAll();

    Object update(@Valid UpdateAccountDTO accountDTO);
}




