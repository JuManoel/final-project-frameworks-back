package edu.ucaldas.back.models.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.ucaldas.back.DTO.UserUpdateDTO;
import edu.ucaldas.back.models.review.UserReview;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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


/**
 * Represents a user entity in the system.
 * <p>
 * This class is mapped to the "users" table in the database and implements the {@link org.springframework.security.core.userdetails.UserDetails}
 * interface for integration with Spring Security.
 * </p>
 * <p>
 * Each user has a unique identifier, name, email, password, user type, a list of reviews, star rating, and an active status.
 * The user type determines the granted authorities (roles) for authentication and authorization purposes.
 * </p>
 *
 * <ul>
 *   <li>{@code id} - Unique identifier for the user.</li>
 *   <li>{@code name} - Name of the user.</li>
 *   <li>{@code email} - Unique email address of the user.</li>
 *   <li>{@code password} - Encrypted password for authentication.</li>
 *   <li>{@code typeUser} - Enum representing the type of user (e.g., ADMIN, CLIENT, OWNER).</li>
 *   <li>{@code reviews} - List of reviews associated with the user.</li>
 *   <li>{@code stars} - Average star rating for the user.</li>
 *   <li>{@code isActive} - Indicates whether the user account is active.</li>
 * </ul>
 *
 * <p>
 * Provides methods for updating user information, managing star ratings, and retrieving user authorities.
 * </p>
 *
 * @author juan-manoel
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    @OneToMany(mappedBy = "userReviewed", fetch = FetchType.LAZY)
    private List<UserReview> reviews;
    private float stars;
    private boolean isActive;

    public User(UserData userData) {
        this.name = userData.name();
        this.email = userData.email();
        this.password = userData.password();
        this.typeUser = TypeUser.valueOf(userData.typeUser());
        this.stars = 0;
        this.isActive = true;
    }
    /**
     * Returns the authorities granted to the user based on their type.
     * <p>
     * This method overrides the {@code getAuthorities} method from the {@link org.springframework.security.core.userdetails.UserDetails} interface.
     * It assigns a specific role to the user depending on the value of {@code typeUser}:
     * <ul>
     *   <li>{@code ADMIN} - returns a list containing {@code ROLE_ADMIN}</li>
     *   <li>{@code CLIENT} - returns a list containing {@code ROLE_CLIENT}</li>
     *   <li>{@code OWNER} - returns a list containing {@code ROLE_OWNER}</li>
     *   <li>Any other value - returns {@code null}</li>
     * </ul>
     *
     * @return a collection of {@link org.springframework.security.core.GrantedAuthority} representing the user's roles,
     *         or {@code null} if the user type is unrecognized.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        switch (this.typeUser) {
            case ADMIN -> {
                return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            case CLIENT -> {
                return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
            }
            case OWNER -> {
                return List.of(new SimpleGrantedAuthority("ROLE_OWNER"));
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String getUsername() {

        return this.email;
    }

    public void updateUser(UserUpdateDTO userUpdateDTO) {
        this.name = userUpdateDTO.newName();
        this.email = userUpdateDTO.newEmail();
    }

    /**
     * Adds a new star rating to the user and updates the average stars.
     * <p>
     * If the user has no reviews, sets the stars to the given value.
     * Otherwise, recalculates the average stars based on the existing reviews and the new rating.
     * </p>
     *
     * @param stars2 the new star rating to add
     */
    public void addStars(int stars2) {
        if (this.reviews == null || this.reviews.isEmpty()) {
            this.stars = stars2;
        } else {
            this.stars = (this.stars * this.reviews.size() + stars2) / (this.reviews.size() + 1);
        }
    }

}
