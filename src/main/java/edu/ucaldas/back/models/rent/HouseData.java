package edu.ucaldas.back.models.rent;

public record HouseData(
        String description,
        AddressData addressData,
        String email,
        float stars) {

}
