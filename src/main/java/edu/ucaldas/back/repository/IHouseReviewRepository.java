package edu.ucaldas.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.review.HouseReview;

/**
 * Repository interface for managing {@link HouseReview} entities, extending the base review repository.
 * <p>
 * Provides methods for retrieving paginated lists of active house reviews for a specific house,
 * as well as calculating the average star rating for a house, considering only active reviews.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     Page&lt;HouseReview&gt; reviews = houseReviewRepository.findByHouseReviewedByHouseId(houseId, pageable);
 *     Float averageStars = houseReviewRepository.findAverageStarsByHouseId(houseId);
 * </pre>
 * </p>
 *
 * @see HouseReview
 * @see IReviewRepository
 */
@Repository
public interface IHouseReviewRepository extends IReviewRepository{

    /**
     * Retrieves a paginated list of active house reviews for a specific house, ordered by review date in descending order.
     *
     * @param id    the unique identifier of the house to retrieve reviews for
     * @param page  the pagination information
     * @return      a page of {@link HouseReview} entities associated with the specified house, only including active reviews
     */
    @Query("SELECT hr FROM HouseReview hr WHERE hr.houseReviewed.id = :houseId AND hr.isActive = true ORDER BY hr.dateTime DESC")
    Page<HouseReview> findByHouseReviewedByHouseId(@Param("houseId")Long id, Pageable page);

    /**
     * Retrieves the average star rating for a specific house, considering only active reviews.
     *
     * @param id the unique identifier of the house whose average star rating is to be calculated
     * @return the average number of stars for the specified house, or {@code null} if there are no active reviews
     */
    @Query("SELECT AVG(hr.stars) FROM HouseReview hr WHERE hr.houseReviewed.id = :houseId AND hr.isActive = true")
    Float findAverageStarsByHouseId(@Param("houseId") Long id);

}
