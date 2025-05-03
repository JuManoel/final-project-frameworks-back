package edu.ucaldas.back.models.rent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HouseImageData(
    @NotBlank @NotNull long houseId
) {

}
