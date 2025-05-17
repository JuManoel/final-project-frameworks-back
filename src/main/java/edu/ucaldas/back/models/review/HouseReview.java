package edu.ucaldas.back.models.review;

import edu.ucaldas.back.models.rent.House;
import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Entity representing a review for a house.
 * <p>
 * Extends the {@link Review} class to include a reference to the reviewed {@link House}.
 * </p>
 *
 * <p>
 * This entity is mapped to the "house_reviews" table in the database.
 * </p>
 *
 * <ul>
 *   <li>{@code houseReviewed}: The house that is being reviewed.</li>
 * </ul>
 *
 * <p>
 * Provides constructors for JPA and for creating a review from data transfer objects.
 * </p>
 */
@Entity
@Table(name = "house_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseReview extends Review {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_reviewed_id")
    private House houseReviewed;

    public HouseReview(HouseReviewData houseReviewData, User writer,House houseReviewed) {
        super(writer, houseReviewData.comment(), houseReviewData.stars());
        this.houseReviewed = houseReviewed;

    }
}
