package com.bank.managment.controller;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.request.UpdateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.service.TransactionService;
import com.bank.managment.service.impl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Tag(name = "transaction-controller", description = "Gestión de transacciones")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/all")
    @Operation(summary = "Obtener todas las transacciones")

    public ResponseEntity<List<TransactionDTO>> getTransaction(){
        return ResponseEntity.ok(transactionService.getAll());
    }

    @PostMapping // /transactions
    public ResponseEntity<TransactionDTO> saveTransaction(@Valid @RequestBody CreateTransactionDTO createTransactionDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.save(createTransactionDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return transactionService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<TransactionDTO> updateTransaction(@Valid @RequestBody UpdateTransactionDTO transactionDTO){
        return ResponseEntity.ok((TransactionDTO) transactionService.update(transactionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transferir dinero entre cuentas")
    public ResponseEntity<TransactionDTO> transfer(
            @RequestParam Long idCuentaOrigen,
            @RequestParam Long idCuentaDestino,
            @RequestParam Double monto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(((TransactionServiceImpl) transactionService)
                        .transfer(idCuentaOrigen, idCuentaDestino, monto));
    }

}
