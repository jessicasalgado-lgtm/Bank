package com.bank.managment.mapper;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "idAccount", ignore = true)
    @Mapping(target = "user", ignore = true)
    Account toEntity(CreateAccountDTO dto);

    @Mapping(target = "idUser",
             expression = "java(account.getUser() == null ? null : account.getUser().getIdUser())")

    AccountDTO toDTO(Account account);

    void updateEntity(@MappingTarget Account account, CreateAccountDTO dto);
}
