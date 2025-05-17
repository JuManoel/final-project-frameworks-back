package edu.ucaldas.back.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.ucaldas.back.models.rent.House;

/**
 * Repository interface for managing {@link House} entities.
 * <p>
 * Provides methods for querying and manipulating house data, including
 * retrieval by ID, existence checks based on address or owner, and
 * paginated queries for available and active houses.
 * </p>
 *
 * <p>
 * Custom query methods include:
 * <ul>
 *   <li>Finding active houses by ID</li>
 *   <li>Checking for the existence of active houses by address or owner email</li>
 *   <li>Retrieving paginated lists of available and active houses</li>
 *   <li>Finding active and unavailable houses by ID</li>
 *   <li>Checking for the existence of houses that are active and available by ID</li>
 * </ul>
 * </p>
 *
 * @author juan-manoel
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see edu.ucaldas.back.model.House
 */
@Repository
public interface IHouseRepository extends JpaRepository<House, Long> {
        /**
         * Retrieves an active {@link House} entity by its unique identifier.
         *
         * @param id the unique identifier of the house to retrieve
         * @return an {@link Optional} containing the active house if found, or empty if not found or inactive
         */
        Optional<House> findByIdAndIsActiveTrue(Long id);

        /**
         * Checks if an active house exists with the specified address details.
         *
         * @param street     the street name of the house address
         * @param city       the city of the house address
         * @param state      the state of the house address
         * @param number     the house number
         * @param complement the complement of the house address (e.g., apartment, suite)
         * @return true if an active house with the given address exists, false otherwise
         */
        @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM House h WHERE h.address.street = :street AND h.address.city = :city AND h.address.state = :state AND h.address.number = :number AND h.address.complement = :complement AND h.isActive = true")
        boolean existsByAddress(@Param("street") String street, @Param("city") String city,
                        @Param("state") String state,
                        @Param("number") String number, @Param("complement") String complement);

        /**
         * Checks if there is at least one active house associated with the owner identified by the given email.
         *
         * @param email the email address of the house owner to search for
         * @return true if an active house exists for the specified owner email, false otherwise
         */
        @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM House h JOIN h.owner o WHERE o.email = :email AND h.isActive = true")
        boolean existsByOwnerEmail(@Param("email") String email);

        /**
         * Retrieves a paginated list of houses that are both active and available.
         *
         * @param pageable the pagination information
         * @return a page of houses that are active and available
         */
        @Query("SELECT h FROM House h WHERE h.isActive = true AND h.isAvailable = true")
        Page<House> findAllAvailableAndActiveHouses(Pageable pageable);

        /**
         * Retrieves an active and unavailable house by its ID.
         *
         * @param id the unique identifier of the house
         * @return an {@link Optional} containing the {@link House} if found and if it is active and unavailable, or an empty {@link Optional} otherwise
         */
        @Query("SELECT h FROM House h WHERE h.isActive = true AND h.isAvailable = false AND h.id = :id")
        Optional<House> findByIdAndIsAvailableFalseAndIsActiveTrue(@Param("id") Long id);

        /**
         * Checks if a house with the specified ID exists, is active, and is available.
         *
         * @param id the unique identifier of the house
         * @return true if a house with the given ID exists, is active, and is available; false otherwise
         */
        boolean existsByIdAndIsActiveTrueAndIsAvailableTrue(Long id);

}
