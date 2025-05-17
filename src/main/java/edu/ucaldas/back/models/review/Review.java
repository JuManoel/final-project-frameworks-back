package edu.ucaldas.back.models.review;

import java.time.LocalDateTime;

import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract base class representing a review entity.
 * <p>
 * This class is mapped to the "reviews" table and uses JOINED inheritance strategy.
 * It contains common fields and logic for all types of reviews.
 * </p>
 *
 * <p>
 * Fields:
 * <ul>
 *   <li><b>id</b>: Unique identifier for the review (auto-generated).</li>
 *   <li><b>writer</b>: The user who wrote the review.</li>
 *   <li><b>comment</b>: The textual content of the review.</li>
 *   <li><b>dateTime</b>: The date and time when the review was created.</li>
 *   <li><b>stars</b>: The rating given in the review (e.g., 1-5 stars).</li>
 *   <li><b>isActive</b>: Indicates whether the review is active or not.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Constructors:
 * <ul>
 *   <li>Default constructor for JPA.</li>
 *   <li>All-args constructor for full initialization.</li>
 *   <li>Convenience constructor for creating a review with writer, comment, and stars.
 *       Sets the current date/time and marks the review as active.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Lombok annotations are used to generate getters, setters, equals, hashCode, and constructors.
 * </p>
 *
 * @author juan-manoel
 */
@Entity
@Table(name = "reviews")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_reviewer_id")
    private User writer;
    private String comment;
    private LocalDateTime dateTime;
    private int stars;
    private boolean isActive;

    public Review(User writer, String comment, int stars) {
        this.writer = writer;
        this.comment = comment;
        this.stars = stars;
        this.dateTime = LocalDateTime.now();
        this.isActive = true;
    }

}
