package edu.ucaldas.back.models.rent;

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
 * Represents an image associated with a house in the system.
 * This entity is mapped to the "house_images" table in the database.
 * Each image is linked to a specific house and contains metadata
 * such as the image URL and its active status.
 * 
 * Annotations:
 * - @Entity: Specifies that this class is an entity and is mapped to a database table.
 * - @Table: Specifies the name of the database table ("house_images").
 * - @Getter, @Setter: Automatically generates getter and setter methods for all fields.
 * - @NoArgsConstructor, @AllArgsConstructor: Generates constructors with no arguments and all arguments, respectively.
 * - @EqualsAndHashCode: Generates equals and hashCode methods based on the "id" field.
 * 
 * Fields:
 * - id: The unique identifier for the image (primary key).
 * - house: The house associated with this image (many-to-one relationship).
 * - imageUrl: The URL of the image.
 * - isActive: Indicates whether the image is active or not.
 */
@Entity
@Table(name = "house_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class HouseImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    private String imageUrl;
    private boolean isActive;

}
