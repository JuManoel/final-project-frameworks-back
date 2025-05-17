package edu.ucaldas.back.models.rent;

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
 * Entity representing an image associated with a house.
 * <p>
 * Each HouseImage instance stores the URL of an image and its active status,
 * and is linked to a specific {@link House} entity.
 * </p>
 *
 * <p>
 * Fields:
 * <ul>
 *   <li><b>id</b>: Unique identifier for the image (primary key).</li>
 *   <li><b>house</b>: The {@link House} entity this image belongs to.</li>
 *   <li><b>imageUrl</b>: The URL or path to the image resource.</li>
 *   <li><b>isActive</b>: Indicates whether the image is currently active.</li>
 * </ul>
 * </p>
 *
 * <p>
 * By default, new images are set as active.
 * </p>
 */
@Entity
@Table(name = "house_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class HouseImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    private House house;
    private String imageUrl;
    private boolean isActive;

    public HouseImage(House house, String imageUrl) {
        this.house = house;
        this.imageUrl = imageUrl;
        this.isActive = true; // Default to active when created
    }

}
