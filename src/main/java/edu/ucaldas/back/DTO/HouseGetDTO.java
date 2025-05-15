package edu.ucaldas.back.DTO;

import edu.ucaldas.back.models.rent.AddressData;

public record HouseGetDTO(
            AddressData addressData,
            String description,
            String emailOwener,
            String nameOwener,
            Float stars,
            long idHouse
) {

}
