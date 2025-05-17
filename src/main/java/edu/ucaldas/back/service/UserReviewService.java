package edu.ucaldas.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.ReviewDTO;
import edu.ucaldas.back.models.review.UserReview;
import edu.ucaldas.back.models.review.UserReviewData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.repository.IUserReviewRepository;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserReviewService {

    @Autowired
    private IUserReviewRepository userReviewRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ValidationsUser validationsUser;

    public Page<ReviewDTO> getUserReviews(String email, Pageable page) {
        if (!validationsUser.existsEmail(email)) {
            throw new EntityNotFoundException("Invalid email format");
        }
        return userReviewRepository.findByUserReviewedEmail(email, page)
                .map(userReview -> new ReviewDTO(userReview.getWriter().getEmail(), userReview.getWriter().getName(),
                        userReview.getComment(), userReview.getStars(), userReview.getDateTime()));

    }

    @Transactional
    public ReviewDTO createUserReview(UserReviewData userReviewData) {
        // Implement the logic to create a new user review
        // This may involve saving the review to the database and returning the created
        // review
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = (User) authentication.getPrincipal();
        if (!validationsUser.existsEmail(userReviewData.userReviewed())) {
            throw new EntityNotFoundException("Invalid user email");
        }
        var userReviewed = userRepository.getUser(userReviewData.userReviewed()).get();
        var userReview = new UserReview(userReviewData, writer, userReviewed);
        userReviewed.addStars(userReview.getStars());
        userReviewRepository.save(userReview);
        userRepository.save(userReviewed);
        return new ReviewDTO(writer.getEmail(), writer.getName(),
                userReview.getComment(), userReview.getStars(), userReview.getDateTime());
    }

}
