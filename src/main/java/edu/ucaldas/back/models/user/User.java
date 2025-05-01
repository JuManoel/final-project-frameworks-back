package edu.ucaldas.back.models.user;

import java.util.List;

import edu.ucaldas.back.models.review.UserReview;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    @OneToMany(mappedBy = "userReviewed")
    private List<UserReview> reviews;
    private float stars;
    private boolean isActive;

    public User(UserData userData) {
        this.name = userData.name();
        this.email = userData.email();
        this.password = userData.password();
        this.typeUser = userData.typeUser();
        this.stars = 0;
        this.isActive = true;
    }

}
