package edu.ucaldas.back.service.validations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.repository.IRentRepository;
import edu.ucaldas.back.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service class providing validation methods related to rental operations.
 * <p>
 * This component offers utility methods to check the existence and status of users, houses, and rents,
 * ensuring that business rules regarding active entities and their associations are enforced.
 * </p>
 * <ul>
 *   <li>Verifies if a user with a given email has an active rent.</li>
 *   <li>Checks if a house with a specific ID currently has an active rent.</li>
 *   <li>Determines if a rent with a given ID exists and is active.</li>
 * </ul>
 * <p>
 * Throws {@link javax.persistence.EntityNotFoundException} when referenced entities are not found or inactive.
 * </p>
 *
 * @author juan-manoel
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
     * Checks if a user with the specified email has an active rent.
     *
     * <p>This method first verifies if an active user exists with the given email.
     * If no such user exists, it throws an {@link EntityNotFoundException}.
     * Otherwise, it checks if there is any active rent associated with the user's email.
     *
     * @param email the email address of the user to check
     * @return {@code true} if the user has an active rent, {@code false} otherwise
     * @throws EntityNotFoundException if no active user is found with the specified email
     */
    public boolean userHasRent(String email) {
        if (!userRepository.existsByEmailAndIsActiveTrue(email)) {
            throw new EntityNotFoundException("User not found");
        }
        return rentRepository.existsActiveRentByUserEmail(email);
    }


    /**
     * Checks if a house with the given ID currently has an active rent.
     *
     * <p>This method first verifies that the house exists and is active. If the house does not exist or is not active,
     * it throws an {@link EntityNotFoundException}. If the house exists and is active, it checks whether there is
     * an active rent associated with the house.</p>
     *
     * @param idHouse the ID of the house to check
     * @return {@code true} if there is an active rent for the house; {@code false} otherwise
     * @throws EntityNotFoundException if the house with the given ID does not exist or is not active
     */
    public boolean houseHasRent(Long idHouse) {
        if (!houseRepository.findByIdAndIsActiveTrue(idHouse).isPresent()) {
            throw new EntityNotFoundException("House not found");
        }
        return rentRepository.existsActiveRentByHouseId(idHouse);
    }

    /**
     * Checks if a rent with the specified ID exists and is active.
     *
     * @param id the ID of the rent to check
     * @return {@code true} if a rent with the given ID exists and is active; {@code false} otherwise
     */
    public boolean existsRent(Long id) {
        return rentRepository.existsByIdAndIsActiveTrue(id);
    }
}
