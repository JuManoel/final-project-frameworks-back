package edu.ucaldas.back.models.rent;

import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a rental entity in the system.
 * This class is mapped to the "rent" table in the database.
 * It contains information about a rental, including the associated house,
 * the locator (user who rents the house), the price, and the rental status.
 * 
 * Annotations:
 * - @Entity: Marks this class as a JPA entity.
 * - @Table: Specifies the table name in the database.
 * - @Getter, @Setter: Automatically generates getter and setter methods for all fields.
 * - @NoArgsConstructor: Generates a no-argument constructor.
 * - @AllArgsConstructor: Generates a constructor with all fields as parameters.
 * - @EqualsAndHashCode: Generates equals and hashCode methods based on the "id" field.
 * 
 * Fields:
 * - id: The unique identifier for the rental (primary key).
 * - house: The house being rented, mapped as a many-to-one relationship.
 * - price: The price of the rental.
 * - locator: The user who rents the house, mapped as a many-to-one relationship.
 * - accepted: Indicates whether the rental has been accepted.
 * - isActive: Indicates whether the rental is currently active.
 * 
 * Constructors:
 * - Rent(): Default no-argument constructor.
 * - Rent(RentData rentData, House house, User locator): Initializes a new Rent instance
 *   with the provided rental data, house, and locator. Sets "accepted" to false and "isActive" to true.
 */
@Entity
@Table(name = "rent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    private float price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User locator;
    private boolean accepted;
    private boolean isActive;

    public Rent(RentData rentData, House house, User locator) {
        this.house = house;
        this.price = rentData.price();
        this.locator = locator;
        this.accepted = false;
        this.isActive = true;
    }
    
}
