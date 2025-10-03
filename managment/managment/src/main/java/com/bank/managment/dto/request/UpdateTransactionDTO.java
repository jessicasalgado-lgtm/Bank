package com.bank.managment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateTransactionDTO {
    private Long id;
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double amount;
    @NotBlank(message = "El tipo de transacción es obligatorio")
    private String type;
    @NotNull(message = "La cuenta asociada es obligatoria")
    private Long idAccount;



}
