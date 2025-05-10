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
 * Represents a user entity in the system. This class is annotated with JPA and 
 * Spring Security annotations to define its persistence and security behavior.
 * It implements the {@link UserDetails} interface to integrate with Spring Security.
 *
 * <p>The User class contains the following fields:</p>
 * <ul>
 *   <li><b>id</b>: The unique identifier for the user, auto-generated.</li>
 *   <li><b>name</b>: The name of the user.</li>
 *   <li><b>email</b>: The email address of the user, which must be unique.</li>
 *   <li><b>password</b>: The password of the user.</li>
 *   <li><b>typeUser</b>: The type of user, represented as an enumerated value.</li>
 *   <li><b>reviews</b>: A list of reviews associated with the user.</li>
 *   <li><b>stars</b>: The average rating of the user, represented as a float.</li>
 *   <li><b>isActive</b>: A boolean indicating whether the user is active.</li>
 * </ul>
 *
 * <p>The class provides the following functionality:</p>
 * <ul>
 *   <li>Mapping user roles to Spring Security authorities based on the user type.</li>
 *   <li>Returning the email as the username for authentication purposes.</li>
 *   <li>Constructors for creating user instances, including one that initializes 
 *       fields from a {@link UserData} object.</li>
 * </ul>
 *
 * <p>Annotations used:</p>
 * <ul>
 *   <li>{@link Entity}: Marks this class as a JPA entity.</li>
 *   <li>{@link Table}: Specifies the table name in the database.</li>
 *   <li>{@link Getter}, {@link Setter}: Lombok annotations to generate getters and setters.</li>
 *   <li>{@link NoArgsConstructor}, {@link AllArgsConstructor}: Lombok annotations to generate constructors.</li>
 *   <li>{@link EqualsAndHashCode}: Lombok annotation to generate equals and hashCode methods based on the "id" field.</li>
 *   <li>{@link Id}, {@link GeneratedValue}: JPA annotations for primary key generation.</li>
 *   <li>{@link Column}: Specifies column constraints, such as uniqueness for the email field.</li>
 *   <li>{@link Enumerated}: Maps the typeUser field to a string representation in the database.</li>
 *   <li>{@link OneToMany}: Defines a one-to-many relationship with the UserReview entity.</li>
 * </ul>
 *
 * <p>Implements:</p>
 * <ul>
 *   <li>{@link UserDetails}: Provides methods required for Spring Security integration.</li>
 * </ul>
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
        this.typeUser = userData.typeUser();
        this.stars = 0;
        this.isActive = true;
    }

    /**
     * Retrieves the collection of authorities granted to the user based on their type.
     * This method maps the user's type to a specific role using Spring Security's 
     * {@link GrantedAuthority}.
     *
     * @return a collection of {@link GrantedAuthority} objects representing the roles 
     *         assigned to the user. Returns {@code null} if the user type does not match 
     *         any predefined roles.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        switch (this.typeUser) {
            case ADMIN -> {
                return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            case TENANT -> {
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }
            case OWNER -> {
                return List.of(new SimpleGrantedAuthority("ROLE_OWNER"));
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Retrieves the username of the user.
     * In this implementation, the username corresponds to the user's email address.
     *
     * @return the email of the user as their username.
     */
    @Override
    public String getUsername() {

        return this.email;
    }

    public void updateUser(UserUpdateDTO userUpdateDTO) {
        this.name = userUpdateDTO.newName();
        this.email = userUpdateDTO.newEmail();
    }

}
