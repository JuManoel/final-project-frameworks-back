package edu.ucaldas.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.user.User;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * Provides methods to perform CRUD operations and custom queries
 * related to active users in the system.
 * </p>
 *
 * <p>
 * Extends {@link JpaRepository} to inherit standard data access methods.
 * </p>
 *
 * <p>
 * Custom queries include finding users by ID or email only if they are active,
 * retrieving user details, and checking for the existence of active users by email.
 * </p>
 *
 * @author juan-manoel
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    /**
     * Retrieves an active user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an {@link Optional} containing the {@link User} if found and active, or empty if not found or inactive
     */
    Optional<User> findByIdAndIsActiveTrue(Long id);

    /**
     * Retrieves the user details for an active user with the specified email address.
     *
     * @param email the email address of the user to search for
     * @return the UserDetails of the active user with the given email, or null if no such user exists
     */
    UserDetails findByEmailAndIsActiveTrue(String email);

    /**
     * Retrieves an active user by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return an {@link Optional} containing the {@link User} if found and active, or empty if not found or inactive
     */
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    Optional<User> getUser(@Param("email") String email);

    /**
     * Checks if a user with the specified email exists and is marked as active.
     *
     * @param email the email address to search for
     * @return true if a user with the given email exists and is active, false otherwise
     */
    boolean existsByEmailAndIsActiveTrue(String email);

}
