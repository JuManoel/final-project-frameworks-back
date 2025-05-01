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
