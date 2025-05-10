package edu.ucaldas.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.UserReviewDTO;
import edu.ucaldas.back.repository.IUserReviewRepository;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserReviewService {

    @Autowired
    private IUserReviewRepository userReviewRepository;

    @Autowired
    private ValidationsUser validationsUser;

    public Page<UserReviewDTO> getUserReviews(String email, Pageable page) {
        if (!validationsUser.existsEmail(email)) {
            throw new EntityNotFoundException("Invalid email format");
        }
        return userReviewRepository.findByUserReviewedEmail(email, page)
                .map(userReview -> new UserReviewDTO(userReview.getUserReviewed().getEmail(),
                        userReview.getWriter().getEmail(), userReview.getComment(), userReview.getStars()));

    }

}
