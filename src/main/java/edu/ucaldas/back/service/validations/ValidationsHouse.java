package edu.ucaldas.back.service.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.models.rent.Address;
import edu.ucaldas.back.models.rent.AddressData;
import edu.ucaldas.back.repository.IHouseRepository;

/**
 * Service class providing validation methods related to house entities.
 * <p>
 * This component offers utility methods to check the existence of houses and addresses,
 * as well as ownership and availability status, by interacting with the house repository.
 * </p>
 *
 * <ul>
 *   <li>Validates if a house exists by its address details.</li>
 *   <li>Checks if an address is already registered in the repository.</li>
 *   <li>Determines if a user owns a house based on their email.</li>
 *   <li>Verifies if a house exists, is active, and is available by its ID.</li>
 * </ul>
 *
 * @author juan-manoel
 * @see edu.ucaldas.back.repository.IHouseRepository
 */
@Component
public class ValidationsHouse {
    @Autowired
    private IHouseRepository houseRepository;

    /**
     * Checks if a house exists in the repository with the specified address details.
     *
     * @param address the Address object containing street, city, state, number, and complement information
     * @return true if a house with the given address exists, false otherwise
     */
    public boolean existsAddress(Address address) {
        String street = address.getStreet();
        String city = address.getCity();
        String state = address.getState();
        String number = address.getNumber();
        String complement = address.getComplement();
        return houseRepository
                .existsByAddress(
                        street, city, state, number, complement);
    }

    /**
     * Checks if an address already exists in the house repository.
     *
     * @param address the {@link AddressData} object containing the address details to check
     * @return {@code true} if the address exists in the repository, {@code false} otherwise
     */
    public boolean existsAddress(AddressData address) {
        String street = address.street();
        String city = address.city();
        String state = address.state();
        String number = address.number();
        String complement = address.complement();
        return houseRepository
                .existsByAddress(
                        street, city, state, number, complement);
    }

    /**
     * Checks if a user, identified by their email, owns a house.
     *
     * @param email the email address of the user to check
     * @return {@code true} if a house exists with the given owner's email, {@code false} otherwise
     */
    public boolean userHasHouse(String email) {
        return houseRepository.existsByOwnerEmail(email);
    }

    /**
     * Checks if a house with the specified ID exists, is active, and is available.
     *
     * @param id the unique identifier of the house to check
     * @return {@code true} if the house exists, is active, and is available; {@code false} otherwise
     */
    public boolean existsHouse(long id) {
        return houseRepository.existsByIdAndIsActiveTrueAndIsAvailableTrue(id);
    }

}
