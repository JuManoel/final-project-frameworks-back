package edu.ucaldas.back.models.rent;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RentData(
        @NotNull long houseId,
        @NotBlank @NotNull @Email String locator,
        @NotNull float price) {

}
