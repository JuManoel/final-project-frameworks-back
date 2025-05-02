package edu.ucaldas.back.models.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an image message entity in the system.
 * This entity is mapped to the "image_message" table in the database.
 * It contains information about the image URL and its active status.
 * 
 * Annotations:
 * - @Entity: Specifies that this class is an entity and is mapped to a database table.
 * - @Table: Specifies the name of the database table ("image_message").
 * - @Getter, @Setter: Automatically generates getter and setter methods for all fields.
 * - @NoArgsConstructor: Generates a no-argument constructor.
 * - @AllArgsConstructor: Generates a constructor with arguments for all fields.
 * - @EqualsAndHashCode: Generates equals and hashCode methods based on the "id" field.
 * 
 * Fields:
 * - id: The unique identifier for the image message. It is auto-generated.
 * - imageUrl: The URL of the image associated with the message.
 * - isActive: A boolean indicating whether the image message is active.
 */
@Entity
@Table(name = "image_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ImageMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String imageUrl;
    private boolean isActive;

}
