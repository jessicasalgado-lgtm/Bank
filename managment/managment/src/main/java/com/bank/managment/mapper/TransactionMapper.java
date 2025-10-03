package com.bank.managment.mapper;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "idTransaction", ignore = true)
    @Mapping(target = "account", ignore = true)
    Transaction toEntity(CreateTransactionDTO dto);

    @Mapping(target = "idAccount",
             expression = "java(transaction.getAccount() == null ? null : transaction.getAccount().getIdAccount())")
    TransactionDTO toDTO(Transaction transaction);

    void updateEntity(@MappingTarget Transaction transaction, CreateTransactionDTO dto);
}

