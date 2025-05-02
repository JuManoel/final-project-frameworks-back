package edu.ucaldas.back.models.review;

import edu.ucaldas.back.models.rent.House;
import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a review for a house, extending the base Review class.
 * This entity is mapped to the "house_review" table in the database.
 * It includes information about the house being reviewed and inherits
 * common review properties such as the writer, comment, and star rating.
 * 
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Entity - Marks this class as a JPA entity.</li>
 *   <li>@Table(name = "house_review") - Specifies the table name in the database.</li>
 *   <li>@Getter, @Setter - Lombok annotations to generate getter and setter methods.</li>
 *   <li>@NoArgsConstructor, @AllArgsConstructor - Lombok annotations to generate constructors.</li>
 * </ul>
 * 
 * <p>Fields:</p>
 * <ul>
 *   <li>houseReviewed - The house being reviewed, represented as a Many-to-One relationship.</li>
 * </ul>
 * 
 * <p>Constructors:</p>
 * <ul>
 *   <li>HouseReview() - No-argument constructor.</li>
 *   <li>HouseReview(HouseReviewData houseReviewData, User writer, House houseReviewed) - 
 *       Constructs a HouseReview with the provided review data, writer, and house being reviewed.</li>
 * </ul>
 */
@Entity
@Table(name = "house_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseReview extends Review {
    @ManyToOne
    @JoinColumn(name = "house_reviewed_id")
    private House houseReviewed;

    public HouseReview(HouseReviewData houseReviewData, User writer,House houseReviewed) {
        super(writer, houseReviewData.comment(), houseReviewData.stars());
        this.houseReviewed = houseReviewed;

    }
}
