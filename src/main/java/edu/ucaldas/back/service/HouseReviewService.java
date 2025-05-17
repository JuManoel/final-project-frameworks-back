package edu.ucaldas.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.ReviewDTO;
import edu.ucaldas.back.models.review.HouseReview;
import edu.ucaldas.back.models.review.HouseReviewData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.repository.IHouseReviewRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class responsible for managing house reviews.
 * <p>
 * Provides operations to retrieve and create reviews for houses, including validation of house existence,
 * retrieval of paginated review data, and handling of review persistence and house rating updates.
 * </p>
 *
 * <p>
 * Main functionalities:
 * <ul>
 *   <li>Retrieve paginated reviews for a specific house.</li>
 *   <li>Create a new review for a house, update the house's star rating, and persist the review.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Dependencies:
 * <ul>
 *   <li>{@link IHouseReviewRepository} for review data access.</li>
 *   <li>{@link ValidationsHouse} for house existence validation.</li>
 *   <li>{@link IHouseRepository} for house data access and updates.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Throws {@link EntityNotFoundException} when operations are attempted on non-existent houses.
 * </p>
 *
 * @author juan-manoel
 */
@Service
public class HouseReviewService {
    @Autowired
    private IHouseReviewRepository houseReviewRepository;

    @Autowired
    private ValidationsHouse validationsHouse;

    @Autowired
    private IHouseRepository houseRepository;

    /**
     * Retrieves a paginated list of reviews for a specific house.
     *
     * @param id   the unique identifier of the house whose reviews are to be fetched
     * @param page the pagination and sorting information
     * @return a {@link Page} of {@link ReviewDTO} objects containing review details for the specified house
     * @throws EntityNotFoundException if the house with the given ID does not exist
     */
    public Page<ReviewDTO> getReviewsHouse(Long id, Pageable page) {
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        Page<ReviewDTO> reviews = houseReviewRepository.findByHouseReviewedByHouseId(id, page)
                .map(houseReview -> new ReviewDTO(houseReview.getWriter().getEmail(), houseReview.getWriter().getName(),
                        houseReview.getComment(), houseReview.getStars(), houseReview.getDateTime()));
        return reviews;
    }

    /**
     * Creates a new review for a house.
     * <p>
     * This method validates the existence of the house, retrieves the currently authenticated user,
     * creates a new {@link HouseReview} entity, updates the house's star rating, and persists both
     * the review and the updated house. Returns a {@link ReviewDTO} containing the review details.
     * </p>
     *
     * @param houseReviewData the data required to create the house review, including house ID, comment, and stars
     * @return a {@link ReviewDTO} containing the reviewer's email, name, comment, stars, and the review date/time
     * @throws EntityNotFoundException if the provided house ID does not correspond to an existing house
     */
    @Transactional
    public ReviewDTO createHouseReview(HouseReviewData houseReviewData) {

        if (!validationsHouse.existsHouse(houseReviewData.houseId())) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = (User) authentication.getPrincipal();  
        var houseReviewed = houseRepository.findByIdAndIsActiveTrue(houseReviewData.houseId()).get();
        HouseReview houseReview = new HouseReview(houseReviewData, writer, houseReviewed);
        houseReviewed.addStars(houseReview.getStars());
        houseReviewRepository.save(houseReview);
        houseRepository.save(houseReviewed);
        return new ReviewDTO(writer.getEmail(), writer.getName(), houseReviewData.comment(), houseReviewData.stars(), houseReview.getDateTime()); // Placeholder return statement
    }

}
