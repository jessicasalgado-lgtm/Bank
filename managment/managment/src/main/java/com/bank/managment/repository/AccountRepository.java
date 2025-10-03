package com.bank.managment.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.managment.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(@NotBlank(message = "El número de cuenta no puede estar vacío") String accountNumber);
}
