package edu.ucaldas.back.models.rent;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HouseData(
        @NotBlank @NotNull String description,
        @Valid AddressData addressData) {

}
