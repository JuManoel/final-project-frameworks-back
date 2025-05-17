package edu.ucaldas.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.ReviewDTO;
import edu.ucaldas.back.infra.exception.NotPermited;
import edu.ucaldas.back.models.review.UserReview;
import edu.ucaldas.back.models.review.UserReviewData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.repository.IUserReviewRepository;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class for managing user reviews.
 * <p>
 * Provides operations to retrieve and create reviews for users, including:
 * <ul>
 *     <li>Fetching paginated reviews for a specific user by email.</li>
 *     <li>Creating new reviews for users, ensuring that users cannot review themselves and that reviewed users exist.</li>
 *     <li>Updating the reviewed user's star rating upon new review creation.</li>
 * </ul>
 * </p>
 * <p>
 * This service relies on repositories for user and review persistence, as well as validation utilities to ensure business rules.
 * </p>
 * 
 * @author juan-manoel
 */
@Service
public class UserReviewService {

    @Autowired
    private IUserReviewRepository userReviewRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ValidationsUser validationsUser;

    /**
     * Retrieves a paginated list of reviews for a user identified by their email.
     *
     * @param email the email address of the user whose reviews are to be fetched
     * @param page the pagination information
     * @return a {@link Page} of {@link ReviewDTO} containing the user's reviews
     * @throws EntityNotFoundException if the provided email does not correspond to an existing user
     */
    public Page<ReviewDTO> getUserReviews(String email, Pageable page) {
        if (!validationsUser.existsUser(email)) {
            throw new EntityNotFoundException("Invalid email format");
        }
        return userReviewRepository.findByUserReviewedEmail(email, page)
                .map(userReview -> new ReviewDTO(userReview.getWriter().getEmail(), userReview.getWriter().getName(),
                        userReview.getComment(), userReview.getStars(), userReview.getDateTime()));

    }

    /**
     * Creates a new user review based on the provided {@link UserReviewData}.
     * <p>
     * This method retrieves the currently authenticated user as the review writer,
     * validates that the reviewed user exists and is not the same as the writer,
     * creates a new {@link UserReview}, updates the reviewed user's star rating,
     * persists the review and the updated user, and returns a {@link ReviewDTO}
     * containing the review details.
     * </p>
     *
     * @param userReviewData the data required to create the user review, including the reviewed user's email, comment, and stars
     * @return a {@link ReviewDTO} containing the reviewer's email, name, comment, stars, and the review date/time
     * @throws EntityNotFoundException if the reviewed user does not exist
     * @throws NotPermited if the writer attempts to review themselves
     */
    @Transactional
    public ReviewDTO createUserReview(UserReviewData userReviewData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = (User) authentication.getPrincipal();
        if (!validationsUser.existsUser(userReviewData.userReviewed())) {
            throw new EntityNotFoundException("Invalid user email");
        }
        if(writer.getEmail().equals(userReviewData.userReviewed())){
            throw new NotPermited("You cannot review yourself");
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
