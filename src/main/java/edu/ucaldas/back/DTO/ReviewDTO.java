package edu.ucaldas.back.DTO;

import java.time.LocalDateTime;

public record ReviewDTO(
    String email,
    String name,
    String comment,
    int stars,
    LocalDateTime postedDate
) {

}
