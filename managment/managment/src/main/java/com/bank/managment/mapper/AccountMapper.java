package com.bank.managment.mapper;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(CreateAccountDTO dto);
    AccountDTO toDTO(Account account);
}
