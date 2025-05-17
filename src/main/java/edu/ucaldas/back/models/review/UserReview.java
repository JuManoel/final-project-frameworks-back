package edu.ucaldas.back.models.review;

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
 * Entity representing a review written by a user about another user.
 * Extends the {@link Review} class to include specific information about the user being reviewed.
 *
 * <p>This class is mapped to the "user_reviews" table in the database.
 *
 * <ul>
 *   <li>{@code userReviewed}: The user who is being reviewed.</li>
 * </ul>
 *
 * <p>Includes constructors for JPA and for creating a review from data transfer objects.
 */
@Entity
@Table(name = "user_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReview extends Review {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_reviewed_id")
    private User userReviewed;

    public UserReview(UserReviewData userReviewData, User writer, User userReviewed) {
        super(writer, userReviewData.comment(), userReviewData.stars());
        this.userReviewed = userReviewed;
    }
}
