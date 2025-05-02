package edu.ucaldas.back.models.review;

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
 * Represents a review written by a user about another user.
 * This class extends the {@link Review} class and includes additional
 * information about the user being reviewed.
 * 
 * <p>
 * The {@code UserReview} entity is mapped to the "user_review" table in the database.
 * It contains a reference to the user being reviewed and inherits properties
 * such as the writer, comment, and star rating from the {@link Review} class.
 * </p>
 * 
 * <p>
 * This class provides a constructor to create a user review using data from
 * {@link UserReviewData}, the writer of the review, and the user being reviewed.
 * </p>
 * 
 * @see Review
 * @see User
 * @see UserReviewData
 */
@Entity
@Table(name = "user_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReview extends Review {
    @ManyToOne
    @JoinColumn(name = "user_reviewed_id")
    private User userReviewed;

    public UserReview(UserReviewData userReviewData, User writer, User userReviewed) {
        super(writer, userReviewData.comment(), userReviewData.stars());
        this.userReviewed = userReviewed;
    }
}
