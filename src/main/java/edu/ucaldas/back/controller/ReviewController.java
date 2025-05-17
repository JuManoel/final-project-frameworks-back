package edu.ucaldas.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ucaldas.back.DTO.ReviewDTO;
import edu.ucaldas.back.models.review.HouseReviewData;
import edu.ucaldas.back.models.review.UserReviewData;
import edu.ucaldas.back.service.HouseReviewService;
import edu.ucaldas.back.service.UserReviewService;
import jakarta.validation.Valid;

/**
 * Controller for handling review-related operations for houses and users.
 * <p>
 * Provides endpoints to retrieve paginated house reviews, create user reviews, and create house reviews.
 * </p>
 * <ul>
 *   <li>
 *     <b>GET /review/house/{id}</b>: Retrieves a paginated list of reviews for a specific house.
 *   </li>
 *   <li>
 *     <b>POST /review/user</b>: Creates a new review for a user.
 *   </li>
 *   <li>
 *     <b>POST /review/house</b>: Creates a new review for a house.
 *   </li>
 * </ul>
 *
 * <p>
 * This controller delegates review operations to the {@link HouseReviewService} and {@link UserReviewService}.
 * </p>
 *
 * @author juan-manoel
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private HouseReviewService houseReviewService;
    @Autowired
    private UserReviewService userReviewService;


    /**
     * Retrieves a paginated list of reviews associated with a specific user identified by their email address.
     *
     * @param email the email address of the user whose reviews are to be fetched; must be a valid email format
     * @param page the pagination information (page number, size, sorting)
     * @return a {@link Page} of {@link ReviewDTO} objects representing the user's reviews
     */
    @GetMapping("house/{id}")
    public Page<ReviewDTO> getHouseReview(@PathVariable Long id ,@PageableDefault(size = 10) Pageable page) {
        return houseReviewService.getReviewsHouse(id, page);
    }

    /**
     * Handles HTTP POST requests to create a new user review.
     *
     * @param userReview the data for the user review to be created, validated from the request body
     * @return the created {@link ReviewDTO} object
     */
    @PostMapping("user")
    public ReviewDTO createUserReview(@RequestBody @Valid UserReviewData userReview) {
        return userReviewService.createUserReview(userReview);
    }

    /**
     * Handles HTTP POST requests to create a new house review.
     *
     * @param houseReviewData the data for the house review to be created, validated from the request body
     * @return the created {@link ReviewDTO} object representing the new house review
     */
    @PostMapping("house")
    public ReviewDTO createHouseReview(@RequestBody @Valid HouseReviewData houseReviewData) {
        return houseReviewService.createHouseReview(houseReviewData);
    }

}
