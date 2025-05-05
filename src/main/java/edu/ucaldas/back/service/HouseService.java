package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.HouseSaveDTO;
import edu.ucaldas.back.infra.exception.EntityAlredyExists;
import edu.ucaldas.back.models.rent.AddressData;
import edu.ucaldas.back.models.rent.House;
import edu.ucaldas.back.models.rent.HouseData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for managing house-related operations.
 * 
 * This service provides methods to handle the creation and validation of house
 * entities, ensuring that the necessary business rules are enforced before
 * saving data to the database.
 * 
 * Dependencies:
 * - IHouseRepository: Repository for accessing house data.
 * - IUserRepository: Repository for accessing user data.
 * - ValidationsHouse: Utility for validating house-related business rules.
 * - ValidationsUser: Utility for validating user-related business rules.
 * 
 * Methods:
 * - saveHouse(HouseData house): Saves a new house entity in the database after
 *   validating the owner and address.
 * 
 * Exceptions:
 * - EntityNotFoundException: Thrown if the owner of the house does not exist.
 * - EntityAlredyExists: Thrown if the address of the house already exists in
 *   the system.
 */
@Service
public class HouseService {

    @Autowired
    private IHouseRepository houseRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ValidationsHouse validationsHouse;
    @Autowired
    private ValidationsUser validationsUser;

    /**
     * Saves a new house entity in the database.
     *
     * @param house The data of the house to be saved, including its owner and address.
     * @return A HouseSaveDTO containing the saved house's address and description.
     * @throws EntityNotFoundException If the owner of the house does not exist.
     * @throws EntityAlredyExists If the address of the house already exists in the system.
     */
    public HouseSaveDTO saveHouse(HouseData house) {
        // Convert HouseData to House entity and save it
        if (!validationsUser.existsUser(house.ownerId())) {
            throw new EntityNotFoundException("User not found");
        }
        User owner = userRepository.findByIdAndIsActiveTrue(house.ownerId()).get();
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

}
