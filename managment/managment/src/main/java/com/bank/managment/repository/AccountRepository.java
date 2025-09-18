package com.bank.managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.managment.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
