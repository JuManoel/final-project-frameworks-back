package edu.ucaldas.back.models.rent;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an address that can be embedded in other entities.
 * This class is annotated with @Embeddable, indicating that it is a 
 * value type and can be embedded in an entity.
 * 
 * <p>Fields:</p>
 * <ul>
 *   <li><b>street</b>: The name of the street.</li>
 *   <li><b>city</b>: The name of the city.</li>
 *   <li><b>state</b>: The name of the state or region.</li>
 *   <li><b>number</b>: The number of the building or house.</li>
 *   <li><b>complement</b>: Additional address details, such as apartment or suite number.</li>
 * </ul>
 * 
 * <p>Constructors:</p>
 * <ul>
 *   <li>A no-argument constructor for creating an empty address.</li>
 *   <li>A parameterized constructor for creating an address with specific values.</li>
 *   <li>A constructor that accepts an {@link AddressData} object to initialize the fields.</li>
 * </ul>
 * 
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Getter and @Setter: Automatically generate getter and setter methods for all fields.</li>
 *   <li>@AllArgsConstructor: Generates a constructor with arguments for all fields.</li>
 *   <li>@NoArgsConstructor: Generates a no-argument constructor.</li>
 * </ul>
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
