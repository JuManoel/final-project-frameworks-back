package edu.ucaldas.back.DTO;

import edu.ucaldas.back.models.rent.AddressData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record HouseUpdateDTO(
                @NotEmpty @NotNull @NotBlank String description,
                @Valid AddressData addressData) {

}
