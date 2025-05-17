package edu.ucaldas.back.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.ucaldas.back.models.rent.Rent;

/**
 * Repository interface for managing {@link Rent} entities.
 * <p>
 * Provides methods for querying and managing rent records, including checks for active rents
 * by user email or house ID, and retrieval of active rents for locators and house owners.
 * </p>
 *
 * <ul>
 *   <li>Checks for existence of active and accepted rents by user email (as owner or locator).</li>
 *   <li>Checks for existence of active rents by house ID.</li>
 *   <li>Retrieves paginated lists of active rents for a locator or house owner by email.</li>
 *   <li>Checks for existence of an active rent by its ID.</li>
 *   <li>Retrieves an active rent by its ID.</li>
 * </ul>
 *
 * Extends {@link org.springframework.data.jpa.repository.JpaRepository} to provide standard CRUD operations.
 *
 * @author juan-manoel
 */
@Repository
public interface IRentRepository extends JpaRepository<Rent, Long> {
    
    /**
     * Checks if there exists at least one active and accepted rent associated with the given user's email.
     * The user can be either the owner of the house or the locator.
     *
     * @param email the email address of the user to check for active rents
     * @return true if there is at least one active and accepted rent for the user, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM Rent r " +
           "WHERE r.isActive = true AND (r.house.owner.email = :email OR r.locator.email = :email) AND r.accepted = true")
    boolean existsActiveRentByUserEmail(@Param("email") String email);

    /**
     * Checks if there is at least one active rent associated with the specified house ID.
     *
     * @param houseId the ID of the house to check for active rents
     * @return true if there is at least one active rent for the given house ID, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
        "FROM Rent r " +
        "WHERE r.isActive = true AND r.house.id = :houseId")
    boolean existsActiveRentByHouseId(@Param("houseId") Long houseId);

    /**
     * Retrieves a paginated list of active Rent entities associated with the specified locator's email.
     *
     * @param email the email address of the locator to filter rents by
     * @param pageable the pagination information
     * @return a page of active Rent entities matching the locator's email
     */
    Page<Rent> findByLocatorEmailAndIsActiveTrue(String email, org.springframework.data.domain.Pageable pageable);

    /**
     * Retrieves a paginated list of active Rent entities associated with a specific house owner's email.
     *
     * @param email the email address of the house owner
     * @param pageable the pagination information
     * @return a page of active Rent entities belonging to the specified house owner
     */
    Page<Rent> findByHouseOwnerEmailAndIsActiveTrue(String email, org.springframework.data.domain.Pageable pageable);
     /**
     * Checks if a rent entity with the specified ID exists and is marked as active.
     *
     * @param id the unique identifier of the rent entity
     * @return true if an active rent entity with the given ID exists, false otherwise
     */
    boolean existsByIdAndIsActiveTrue(Long id);

    /**
     * Retrieves an active {@link Rent} entity by its unique identifier.
     *
     * @param id the unique identifier of the rent entity
     * @return an {@link Optional} containing the active rent entity if found, or empty if not found or inactive
     */
    Optional<Rent> findByIdAndIsActiveTrue(long id);
}
