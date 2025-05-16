package edu.ucaldas.back.models.rent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressData(String street,
        @NotBlank @NotNull String city,
        @NotBlank @NotNull String state,
        @NotBlank @NotNull String number,
        String complement) {

}
