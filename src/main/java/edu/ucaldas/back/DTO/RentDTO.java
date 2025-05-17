package edu.ucaldas.back.DTO;

public record RentDTO(
    long id,
    long houseId,
    String email,
    boolean accepted
) {

}
