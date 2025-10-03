package com.bank.managment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateAccountDTO {
    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Long id;
    @NotBlank(message = "El número de cuenta no puede estar vacío")
    private String accountNumber;
    @NotBlank(message = "El tipo de cuenta no puede estar vacío")
    private String accountType;
    @NotNull(message = "El balance no puede ser nulo")
    @PositiveOrZero(message = "El balance no puede ser negativo")
    private Double balance;
    private Long idUser;


}
