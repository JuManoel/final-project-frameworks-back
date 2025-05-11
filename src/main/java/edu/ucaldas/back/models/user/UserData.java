package edu.ucaldas.back.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserData(
        @NotBlank(message = "Falto Escribir el nombre") @NotNull(message = "El nombre no puede ser null") String name,
        @NotBlank(message = "Falto Escribir el email") @NotNull(message = "El email no puede ser null") @Email String email,
        @NotBlank @NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo 1 mayúscula, 1 minúscula y 1 número") String password,
        @NotBlank(message = "Deve especificar el usuario") @NotNull(message = "El tipo de usuario no puede ser null") String typeUser) {

}
