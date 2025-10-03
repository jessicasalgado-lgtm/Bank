package com.bank.managment.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.managment.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(@Email(message = "El email no es válido") @NotBlank(message = "El email es obligatorio") String email);
}
