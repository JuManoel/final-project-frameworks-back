package edu.ucaldas.back.DTO;

import edu.ucaldas.back.models.rent.AddressData;

public record HouseSaveDTO(
    AddressData addressData,
    String description
) {

}
