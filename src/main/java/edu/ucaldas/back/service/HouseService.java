package edu.ucaldas.back.service;

import java.util.Optional;

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
