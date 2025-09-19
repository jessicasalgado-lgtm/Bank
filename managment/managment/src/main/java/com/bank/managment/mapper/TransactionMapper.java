package com.bank.managment.mapper;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toEntity(CreateTransactionDTO dto);
    TransactionDTO toDTO(Transaction transaction);
}
