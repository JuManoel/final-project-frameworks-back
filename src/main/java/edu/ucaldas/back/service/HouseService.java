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
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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
 * validating the owner and address.
 * 
 * Exceptions:
 * - EntityNotFoundException: Thrown if the owner of the house does not exist.
 * - EntityAlredyExists: Thrown if the address of the house already exists in
 * the system.
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
     * @param house The data of the house to be saved, including its owner and
     *              address.
     * @return A HouseSaveDTO containing the saved house's address and description.
     * @throws EntityNotFoundException If the owner of the house does not exist.
     * @throws EntityAlredyExists      If the address of the house already exists in
     *                                 the system.
     */
    public HouseSaveDTO saveHouse(HouseData house) {
        // Convert HouseData to House entity and save it
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

    private AddressData convertToAddressData(House house) {
        return new AddressData(house.getAddress().getStreet(), house.getAddress().getCity(),
                house.getAddress().getState(), house.getAddress().getNumber(), house.getAddress().getComplement());
    }

    public Page<HouseGetDTO> getHouses(Pageable page) {
        var houses = houseRepository.findAllAvailableAndActiveHouses(page);
        return houses.map(house -> new HouseGetDTO(convertToAddressData(house), house.getDescription(),
                house.getOwner().getEmail(), house.getOwner().getName(), house.getStars(), house.getId()));
    }

    public HouseGetDTO getHouse(Long id) {
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        var house = houseRepository.findByIdAndIsActiveTrue(id).get();
        return new HouseGetDTO(convertToAddressData(house), house.getDescription(), house.getOwner().getEmail(),
                house.getOwner().getName(), house.getStars(), house.getId());
    }

    @Transactional
    public HouseUpdateDTO updateHouse(long id, HouseUpdateDTO houseUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        var house = houseRepository.findByIdAndIsActiveTrue(id).get();
        if (!house.getAddress().equals(new Address(houseUpdate.addressData()))  && validationsHouse.existsAddress(houseUpdate.addressData())) {
            throw new EntityAlredyExists("Address already exists");
        }
        if(!house.getOwner().getEmail().equals(user.getEmail())){
            throw new NotPermited("You are not the owner of this house");
        }
        house.updateHouse(houseUpdate);
        return houseUpdate;
    }

    @Transactional
    public void deleteHouse(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        var house = houseRepository.findByIdAndIsActiveTrue(id).get();
        if(!house.getOwner().getEmail().equals(user.getEmail())){
            throw new NotPermited("You are not the owner of this house");
        }
        house.setActive(false);
        houseRepository.save(house);
    }

}
