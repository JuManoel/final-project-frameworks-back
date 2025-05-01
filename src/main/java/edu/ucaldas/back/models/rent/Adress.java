package edu.ucaldas.back.models.rent;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Adress {
    private String street;
    private String city;
    private String state;
    private String number;
    private String complement;

    public Adress(AdressData adressData) {
        this.street = adressData.street();
        this.city = adressData.city();
        this.state = adressData.state();
        this.number = adressData.number();
        this.complement = adressData.complement();
    }
}
