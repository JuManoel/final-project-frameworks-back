package edu.ucaldas.back.models.rent;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an embeddable address entity for use in JPA/Hibernate.
 * Contains fields for street, city, state, number, and complement.
 * 
 * <p>
 * This class provides constructors for creating an Address from individual fields
 * or from an {@code AddressData} object. It also overrides {@code equals} to allow
 * for logical comparison of Address instances based on their field values.
 * </p>
 * 
 * <p>
 * Lombok annotations are used to generate getters, setters, and constructors.
 * </p>
 * 
 * @see javax.persistence.Embeddable
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private String number;
    private String complement;

    public Address(AddressData addressData) {
        this.street = addressData.street();
        this.city = addressData.city();
        this.state = addressData.state();
        this.number = addressData.number();
        this.complement = addressData.complement();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Address address = (Address) obj;
        return (street == null ? address.street == null : street.equals(address.street)) &&
               (city == null ? address.city == null : city.equals(address.city)) &&
               (state == null ? address.state == null : state.equals(address.state)) &&
               (number == null ? address.number == null : number.equals(address.number)) &&
               (complement == null ? address.complement == null : complement.equals(address.complement));
    }
}
