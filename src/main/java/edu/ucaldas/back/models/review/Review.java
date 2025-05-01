package edu.ucaldas.back.models.review;

import java.time.LocalDateTime;

import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "review")
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
    @ManyToOne
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
