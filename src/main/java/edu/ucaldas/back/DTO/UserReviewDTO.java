package edu.ucaldas.back.DTO;

public record UserReviewDTO(
    String userReviewedEmail, 
    String userReviewerEmail, 
    String content, 
    int stars
) {

}
