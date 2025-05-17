package edu.ucaldas.back.models.rent;

import java.util.List;

import edu.ucaldas.back.DTO.HouseUpdateDTO;
import edu.ucaldas.back.models.review.HouseReview;
import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a House entity in the rental system.
 * <p>
 * This class is mapped to the "houses" table in the database and contains information
 * about a house available for rent, including its description, address, owner, reviews,
 * images, availability, and status.
 * </p>
 *
 * <p>
 * Relationships:
 * <ul>
 *   <li>{@link Address} is embedded to represent the house's location.</li>
 *   <li>{@link User} is the owner of the house (ManyToOne relationship).</li>
 *   <li>{@link HouseReview} is a list of reviews associated with the house (OneToMany relationship).</li>
 *   <li>{@link HouseImage} is a list of images associated with the house (OneToMany relationship).</li>
 * </ul>
 * </p>
 *
 * <p>
 * Provides methods to:
 * <ul>
 *   <li>Initialize a house from data transfer objects.</li>
 *   <li>Add star ratings and update the average stars.</li>
 *   <li>Update house details from update DTOs.</li>
 * </ul>
 * </p>
 *
 * @author juan-manoel
 */
@Entity
@Table(name = "houses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @Embedded
    private Address address;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(mappedBy = "houseReviewed", fetch = FetchType.LAZY)
    private List<HouseReview> reviews;
    private float stars;
    private boolean isAvailable;
    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private List<HouseImage> images;
    private boolean isActive;

    public House(HouseData houseData, User owner) {
        this.description = houseData.description();
        this.address = new Address(houseData.addressData());
        this.owner = owner;
        this.stars = 0;
        this.isAvailable = true;
        this.isActive = true;
    }

    public void addStars(int stars2) {
        if (this.reviews == null || this.reviews.isEmpty()) {
            this.stars = stars2;
        } else {
            this.stars = (this.stars * this.reviews.size() + stars2) / (this.reviews.size() + 1);
        }
    }

    public void updateHouse(HouseUpdateDTO houseUpdateDTO){
        this.description = houseUpdateDTO.description();
        this.address.setStreet(houseUpdateDTO.addressData().street());
        this.address.setCity(houseUpdateDTO.addressData().city());
        this.address.setState(houseUpdateDTO.addressData().state());
        this.address.setNumber(houseUpdateDTO.addressData().number());
        this.address.setComplement(houseUpdateDTO.addressData().complement());
    }

}
