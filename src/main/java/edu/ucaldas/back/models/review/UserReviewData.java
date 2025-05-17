package edu.ucaldas.back.models.review;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserReviewData(
        @NotBlank(message = "Falto Escribir el email") @NotNull(message = "El email no puede ser null") @Email String userReviewed,
        @NotBlank(message = "Falto escribir el comentario") @NotNull(message = "El comentario no puede ser null") String comment,
        @NotNull @Min(value = 0, message = "Las estrellas no pueden ser menores a 0") @Max(value = 5, message = "Las estrellas no pueden ser mayores a 5") int stars) {
}
