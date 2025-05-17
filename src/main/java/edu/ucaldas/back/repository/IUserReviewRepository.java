package edu.ucaldas.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.review.UserReview;

/**
 * Repository interface for managing {@link UserReview} entities with additional query methods
 * specific to user-reviewed email and average star ratings.
 * <p>
 * Extends {@link IReviewRepository} to inherit basic CRUD and review-related operations.
 * </p>
 * <ul>
 *   <li>
 *     {@link #findByUserReviewedEmail(String, Pageable)}: Retrieves a paginated list of active user reviews
 *     for a specific user identified by their email address.
 *   </li>
 *   <li>
 *     {@link #findAverageStarsByUserReviewedEmail(String)}: Calculates the average star rating for a user
 *     based on active reviews associated with their email address.
 *   </li>
 * </ul>
 * <p>
 * Annotated with {@link org.springframework.stereotype.Repository} to indicate its role in persistence operations.
 * </p>
 */
@Repository
public interface IUserReviewRepository extends IReviewRepository {

    /**
     * Retrieves a paginated list of active {@link UserReview} entities where the reviewed user's email matches the specified value.
     *
     * @param email the email address of the user who was reviewed
     * @param page the pagination information
     * @return a page of active {@link UserReview} entities for the specified user
     */
    @Query("SELECT ur FROM UserReview ur WHERE ur.userReviewed.email = :email AND ur.isActive = true")
    Page<UserReview> findByUserReviewedEmail(@Param("email") String email, Pageable page);
    
    /**
     * Retrieves the average star rating for a user based on their email address.
     * Only considers active user reviews.
     *
     * @param email the email address of the user whose reviews are being averaged
     * @return the average number of stars for the specified user, or null if no reviews are found
     */
    @Query("SELECT AVG(ur.stars) FROM UserReview ur WHERE ur.userReviewed.email = :email AND ur.isActive = true")
    Float findAverageStarsByUserReviewedEmail(@Param("email") String email);

}
