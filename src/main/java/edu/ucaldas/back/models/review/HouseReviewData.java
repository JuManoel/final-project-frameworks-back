package edu.ucaldas.back.models.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HouseReviewData(
        @NotBlank @NotNull long writerId,
        @NotBlank @NotNull long houseId,
        @NotBlank(message = "Falto escribir el comentario") @NotNull(message = "El comentario no puede ser null") String comment,
        @NotNull @Min(value = 0, message = "Las estrellas no pueden ser menores a 0") @Max(value = 5, message = "Las estrellas no pueden ser mayores a 5") int stars) {

}
