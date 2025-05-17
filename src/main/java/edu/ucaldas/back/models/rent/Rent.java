package edu.ucaldas.back.models.rent;

import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
 * Represents a rental agreement between a user (locator) and a house.
 * 
 * <p>This entity maps to the "rents" table in the database and contains information
 * about the rented house, the user who is renting (locator), the rental price,
 * and the status of the rental (accepted and active).</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>id</b>: Unique identifier for the rent.</li>
 *   <li><b>house</b>: The house being rented.</li>
 *   <li><b>price</b>: The price of the rent.</li>
 *   <li><b>locator</b>: The user who is renting the house.</li>
 *   <li><b>accepted</b>: Indicates if the rent has been accepted.</li>
 *   <li><b>isActive</b>: Indicates if the rent is currently active.</li>
 * </ul>
 *
 * <p>Constructors are provided for JPA and for creating a new Rent from RentData, House, and User.</p>
 */
@Entity
@Table(name = "rents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    private House house;
    private float price;
    @ManyToOne(fetch = FetchType.EAGER)
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
