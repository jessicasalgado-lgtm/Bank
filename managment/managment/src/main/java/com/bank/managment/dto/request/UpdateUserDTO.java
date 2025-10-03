package com.bank.managment.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;


}
