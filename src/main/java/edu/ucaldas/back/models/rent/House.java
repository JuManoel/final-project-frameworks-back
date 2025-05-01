package edu.ucaldas.back.models.rent;

import java.util.List;

import edu.ucaldas.back.models.review.HouseReview;
import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "house")
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
    private Adress adress;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(mappedBy = "houseReviewed")
    private List<HouseReview> reviews;
    private float stars;
    private boolean isAvailable;
    @OneToMany(mappedBy = "house")
    private List<HouseImages> images;
    private boolean isActive;

    public House(HouseData houseData, User owner) {
        this.description = houseData.description();
        this.adress = new Adress(houseData.adressData());
        this.owner = owner;
        this.stars = 0;
        this.isAvailable = true;
        this.isActive = true;
    }

}
