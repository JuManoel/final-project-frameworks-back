package edu.ucaldas.back.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
    @NotBlank(message = "Falto Escribir el nombre") @NotNull(message = "El nombre no puede ser null")String newName,
    @NotBlank(message = "Falto Escribir el email") @NotNull(message = "El email no puede ser null") String email,
    @NotBlank(message = "Falto Escribir el email") @NotNull(message = "El email no puede ser null")String newEmail) {

}
