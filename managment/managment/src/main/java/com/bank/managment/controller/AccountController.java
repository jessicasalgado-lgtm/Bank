package com.bank.managment.controller;

import com.bank.managment.dto.request.CreateAccountDTO;
import com.bank.managment.dto.request.UpdateAccountDTO;
import com.bank.managment.dto.response.AccountDTO;
import com.bank.managment.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "account-controller", description = "Gestión de cuentas")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todas las cuentas")

    public ResponseEntity<List<AccountDTO>> getAccount(){
        return ResponseEntity.ok(accountService.getAll());
    }

    @PostMapping // /accounts
    public ResponseEntity<AccountDTO> saveAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(createAccountDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return accountService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<AccountDTO> updateAccount(@Valid @RequestBody UpdateAccountDTO AccountDTO){
        return ResponseEntity.ok((AccountDTO) accountService.update(AccountDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


