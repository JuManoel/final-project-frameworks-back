package edu.ucaldas.back.models.rent;

public record HouseData(
        String description,
        AdressData adressData,
        long ownerId,
        float stars) {

}
