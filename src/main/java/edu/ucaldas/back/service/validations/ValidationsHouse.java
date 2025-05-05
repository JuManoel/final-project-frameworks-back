package edu.ucaldas.back.service.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.models.rent.Address;
import edu.ucaldas.back.models.rent.AddressData;
import edu.ucaldas.back.repository.IHouseRepository;

/**
 * The ValidationsHouse class provides validation methods for checking the existence
 * of addresses and verifying house ownership based on user email addresses.
 * 
 * <p>This class interacts with the {@link IHouseRepository} to perform the following:
 * <ul>
 *   <li>Check if a specific address exists in the repository.</li>
 *   <li>Verify if a user owns a house based on their email address.</li>
 * </ul>
 * 
 * <p>It supports validation using both {@link Address} and {@link AddressData} objects
 * for address-related checks.
 * 
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IHouseRepository}: Repository interface for house-related data operations.</li>
 * </ul>
 * 
 * <p>Methods:
 * <ul>
 *   <li>{@code existsAddress(Address address)}: Checks if an address exists in the repository.</li>
 *   <li>{@code existsAddress(AddressData address)}: Checks if an address exists in the repository using AddressData.</li>
 *   <li>{@code userHasHouse(String email)}: Verifies if a user owns a house based on their email.</li>
 * </ul>
 * 
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Component}: Marks this class as a Spring component for dependency injection.</li>
 *   <li>{@code @Autowired}: Injects the {@link IHouseRepository} dependency.</li>
 * </ul>
 */
@Component
public class ValidationsHouse {
    @Autowired
    private IHouseRepository houseRepository;

    /**
     * Checks if an address already exists in the repository.
     *
     * @param address The Address object containing the details of the address
     *                to be checked, including street, city, state, number, and complement.
     * @return {@code true} if the address exists in the repository, {@code false} otherwise.
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
     * Checks if an address exists in the house repository.
     *
     * @param address The address data to check, containing street, city, state, 
     *                number, and complement information.
     * @return {@code true} if the address exists in the repository, 
     *         {@code false} otherwise.
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
     * Checks if a user owns a house based on their email address.
     *
     * @param email the email address of the user to check
     * @return true if the user owns a house, false otherwise
     */
    public boolean userHasHouse(String email) {
        return houseRepository.existsByOwnerEmail(email);
    }
}
