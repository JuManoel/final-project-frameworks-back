package edu.ucaldas.back.DTO;

public record TokenDTO(String token,
    String email,
    String name,
    String typeUString
) {
}
