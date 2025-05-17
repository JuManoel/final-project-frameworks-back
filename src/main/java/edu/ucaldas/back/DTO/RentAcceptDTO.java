package edu.ucaldas.back.DTO;

import jakarta.validation.constraints.NotNull;

public record RentAcceptDTO(
    @NotNull long id,
    @NotNull boolean accepted
) {

}
