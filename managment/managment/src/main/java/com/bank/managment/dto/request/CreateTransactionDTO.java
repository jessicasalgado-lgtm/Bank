package com.bank.managment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionDTO {
    @NotBlank(message = "El tipo de transacción es obligatorio")
    private String transactionType;
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double amount;
    @NotNull(message = "La cuenta asociada es obligatoria")
    private Long idAccount;
}
