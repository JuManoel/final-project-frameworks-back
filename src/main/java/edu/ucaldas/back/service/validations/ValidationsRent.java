package edu.ucaldas.back.service.validations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.repository.IRentRepository;
import edu.ucaldas.back.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * The ValidationsRent class provides methods to validate rental-related operations.
 * It includes checks for active rents associated with users and houses.
 * 
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link IRentRepository} - Repository for rent-related operations.</li>
 *   <li>{@link IHouseRepository} - Repository for house-related operations.</li>
 *   <li>{@link IUserRepository} - Repository for user-related operations.</li>
 * </ul>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #userHasRent(String)} - Checks if a user with the given email has an active rent.</li>
 *   <li>{@link #houseHasRent(Long)} - Checks if a house with the given ID has an active rent.</li>
 * </ul>
 * 
 * <p>Exceptions:</p>
 * <ul>
 *   <li>{@link EntityNotFoundException} - Thrown if the user or house does not exist or is not active.</li>
 * </ul>
 * 
 * <p>Usage:</p>
 * <pre>
 * {@code
 * ValidationsRent validationsRent = new ValidationsRent();
 * boolean hasRent = validationsRent.userHasRent("user@example.com");
 * }
 * </pre>
 */
@Component
public class ValidationsRent {
    @Autowired
    private IRentRepository rentRepository;

    @Autowired
    private IHouseRepository houseRepository;

    @Autowired
    private IUserRepository userRepository;

    /**
     * Checks if a user with the given email has an active rent.
     *
     * @param email the email of the user to check
     * @return true if the user has an active rent, false otherwise
     * @throws EntityNotFoundException if the user with the given email does not exist
     */
    public boolean userHasRent(String email) {
        if (!userRepository.existsByEmailAndIsActiveTrue(email)) {
            throw new EntityNotFoundException("User not found");
        }
        return rentRepository.existsActiveRentByUserEmail(email);
    }

    /**
     * Checks if a house with the given ID has an active rent.
     *
     * @param idHouse the ID of the house to check
     * @return {@code true} if the house has an active rent, {@code false} otherwise
     * @throws EntityNotFoundException if the house with the given ID is not found or is not active
     */
    public boolean houseHasRent(Long idHouse) {
        if (!houseRepository.findByIdAndIsActiveTrue(idHouse).isPresent()) {
            throw new EntityNotFoundException("House not found");
        }
        return rentRepository.existsActiveRentByHouseId(idHouse);
    }
}
