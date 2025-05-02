package edu.ucaldas.back.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(
    @NotBlank(message = "Falto Escribir el email") @NotNull(message = "El email no puede ser null")String email,
    @NotBlank(message = "Falto escribir la contraseña") @NotNull(message = "La contraseña no puede ser null")String password
) {

}
