package edu.ucaldas.back.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdatePasswordDTO(
        @NotBlank(message = "Falto Escribir el email") @NotNull(message = "El email no puede ser null") String email,
        @NotBlank @NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo 1 mayúscula, 1 minúscula y 1 número") String password,
        @NotBlank @NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo 1 mayúscula, 1 minúscula y 1 número") String newPassword) {

}
