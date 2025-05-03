package edu.ucaldas.back.service.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.models.rent.Address;
import edu.ucaldas.back.models.rent.AddressData;
import edu.ucaldas.back.repository.IHouseRepository;

@Component
public class ValidationsHouse {
    @Autowired
    private IHouseRepository houseRepository;

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
}
