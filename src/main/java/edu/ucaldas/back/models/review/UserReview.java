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
