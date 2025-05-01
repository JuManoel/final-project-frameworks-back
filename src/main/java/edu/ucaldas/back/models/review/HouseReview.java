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
