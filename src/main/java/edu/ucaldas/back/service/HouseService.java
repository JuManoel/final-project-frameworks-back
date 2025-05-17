package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.HouseGetDTO;
import edu.ucaldas.back.DTO.HouseSaveDTO;
import edu.ucaldas.back.DTO.HouseUpdateDTO;
import edu.ucaldas.back.infra.exception.EntityAlredyExists;
import edu.ucaldas.back.infra.exception.NotPermited;
import edu.ucaldas.back.models.rent.Address;
import edu.ucaldas.back.models.rent.AddressData;
import edu.ucaldas.back.models.rent.House;
import edu.ucaldas.back.models.rent.HouseData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import edu.ucaldas.back.service.validations.ValidationsRent;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class for managing house-related operations.
 * <p>
 * This service provides methods for creating, retrieving, updating, and deleting houses.
 * It ensures that business rules are enforced, such as address uniqueness, ownership validation,
 * and active rent checks before deletion. The service interacts with the house repository and
 * validation components to perform its operations, and it uses Spring Security to determine
 * the currently authenticated user.
 * </p>
 *
 * <p>
 * Main functionalities include:
 * <ul>
 *   <li>Saving a new house associated with the authenticated user, ensuring address uniqueness.</li>
 *   <li>Retrieving paginated lists of available and active houses, or a single house by ID.</li>
 *   <li>Updating house details, with checks for ownership and address conflicts.</li>
 *   <li>Deleting (deactivating) a house, ensuring the user is the owner and there are no active rents.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Exceptions thrown by this service include:
 * <ul>
 *   <li>{@link EntityAlredyExists} if a house with the given address already exists.</li>
 *   <li>{@link EntityNotFoundException} if a house with the specified ID does not exist or is inactive.</li>
 *   <li>{@link NotPermited} if the authenticated user is not permitted to perform the operation.</li>
 * </ul>
 * </p>
 *
 * @author juan-manoel
 */
@Service
public class HouseService {

    @Autowired
    private IHouseRepository houseRepository;
    @Autowired
    private ValidationsHouse validationsHouse;
    @Autowired
    private ValidationsRent validationsRent;


    /**
     * Saves a new house entity associated with the currently authenticated user.
     * <p>
     * This method first checks if the provided address already exists in the system.
     * If it does, an {@link EntityAlredyExists} exception is thrown. Otherwise, a new
     * {@link House} is created and persisted. The method then constructs and returns
     * a {@link HouseSaveDTO} containing the saved house's address and description.
     * </p>
     *
     * @param house the data transfer object containing the house and address information to be saved
     * @return a {@link HouseSaveDTO} containing the saved house's address and description
     * @throws EntityAlredyExists if a house with the given address already exists
     * @return the updated house data as a {@link HouseSaveDTO}
     */
    public HouseSaveDTO saveHouse(HouseData house) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User owner = (User) authentication.getPrincipal();
        if (validationsHouse.existsAddress(house.addressData())) {
            throw new EntityAlredyExists("Address already exists");
        }
        House newHouse = new House(house, owner);
        houseRepository.save(newHouse);
        var address = newHouse.getAddress();
        AddressData addressData = new AddressData(address.getStreet(), address.getCity(), address.getState(),
                address.getNumber(), address.getComplement());
        HouseSaveDTO houseSaveDTO = new HouseSaveDTO(addressData, newHouse.getDescription());
        return houseSaveDTO;
    }

    /**
     * Converts a {@link House} object to an {@link AddressData} object by extracting
     * the address details from the given house.
     *
     * @param house the {@link House} object containing the address information
     * @return an {@link AddressData} object populated with the street, city, state,
     *         number, and complement from the house's address
     */
    private AddressData convertToAddressData(House house) {
        return new AddressData(house.getAddress().getStreet(), house.getAddress().getCity(),
                house.getAddress().getState(), house.getAddress().getNumber(), house.getAddress().getComplement());
    }

    /**
     * Retrieves a paginated list of available and active houses, mapping each house entity
     * to a {@link HouseGetDTO} object.
     *
     * @param page the pagination information
     * @return a {@link Page} of {@link HouseGetDTO} containing house details
     */
    public Page<HouseGetDTO> getHouses(Pageable page) {
        var houses = houseRepository.findAllAvailableAndActiveHouses(page);
        return houses.map(house -> new HouseGetDTO(convertToAddressData(house), house.getDescription(),
                house.getOwner().getEmail(), house.getOwner().getName(), house.getStars(), house.getId()));
    }

    /**
     * Retrieves a house by its ID and returns its details as a HouseGetDTO.
     *
     * @param id the unique identifier of the house to retrieve
     * @return a HouseGetDTO containing the house's address, description, owner's email and name, stars, and ID
     * @throws EntityNotFoundException if the house with the given ID does not exist or is not active
     */
    public HouseGetDTO getHouse(Long id) {
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        var house = houseRepository.findByIdAndIsActiveTrue(id).get();
        return new HouseGetDTO(convertToAddressData(house), house.getDescription(), house.getOwner().getEmail(),
                house.getOwner().getName(), house.getStars(), house.getId());
    }

    /**
     * Updates the details of an existing house with the provided information.
     * <p>
     * This method performs the following steps:
     * <ul>
     *   <li>Retrieves the currently authenticated user.</li>
     *   <li>Checks if the house with the given ID exists; throws {@link EntityNotFoundException} if not.</li>
     *   <li>Checks if the new address is different and already exists; throws {@link EntityAlredyExists} if so.</li>
     *   <li>Verifies that the authenticated user is the owner of the house; throws {@link NotPermited} if not.</li>
     *   <li>Updates the house entity with the new data.</li>
     * </ul>
     *
     * @param id the ID of the house to update
     * @param houseUpdate the DTO containing updated house information
     * @return the updated house data as a {@link HouseUpdateDTO}
     * @throws EntityNotFoundException if the house ID is invalid
     * @throws EntityAlredyExists if the new address already exists
     * @throws NotPermited if the authenticated user is not the owner of the house
     */
    @Transactional
    public HouseUpdateDTO updateHouse(long id, HouseUpdateDTO houseUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        var house = houseRepository.findByIdAndIsActiveTrue(id).get();
        if (!house.getAddress().equals(new Address(houseUpdate.addressData()))
                && validationsHouse.existsAddress(houseUpdate.addressData())) {
            throw new EntityAlredyExists("Address already exists");
        }
        if (!house.getOwner().getEmail().equals(user.getEmail())) {
            throw new NotPermited("You are not the owner of this house");
        }
        house.updateHouse(houseUpdate);
        return houseUpdate;
    }

    /**
     * Deletes (deactivates) a house by its ID if the current authenticated user is the owner
     * and the house does not have any active rent. The house is marked as inactive instead of
     * being physically removed from the database.
     *
     * @param id the ID of the house to delete
     * @throws EntityNotFoundException if the house with the given ID does not exist
     * @throws NotPermited if the current user is not the owner of the house or if the house has an active rent
     */
    @Transactional
    public void deleteHouse(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        var house = houseRepository.findByIdAndIsActiveTrue(id).get();
        if (!house.getOwner().getEmail().equals(user.getEmail())) {
            throw new NotPermited("You are not the owner of this house");
        }
        if(validationsRent.houseHasRent(id)){
            throw new NotPermited("You cannot delete a house that has an active rent");
        }
        house.setActive(false);
        houseRepository.save(house);
    }

}
