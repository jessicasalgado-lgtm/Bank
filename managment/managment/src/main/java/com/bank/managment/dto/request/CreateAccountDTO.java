
package com.bank.managment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateAccountDTO {
    @NotBlank(message = "El número de cuenta no puede estar vacío")
    private String accountNumber;
    @NotBlank(message = "El tipo de cuenta no puede estar vacío")
    private String accountType;
    @NotNull(message = "El balance no puede ser nulo")
    @PositiveOrZero(message = "El balance no puede ser negativo")
    private Double balance;
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUser;

}

