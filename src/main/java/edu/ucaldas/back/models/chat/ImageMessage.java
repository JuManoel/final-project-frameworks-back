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
 * Entity representing an image message in the chat system.
 * <p>
 * This class maps to the "image_messages" table in the database and stores information
 * about image messages, including their unique identifier, image URL, and active status.
 * </p>
 *
 * Fields:
 * <ul>
 *   <li><b>id</b>: Unique identifier for the image message (auto-generated).</li>
 *   <li><b>imageUrl</b>: URL pointing to the image resource.</li>
 *   <li><b>isActive</b>: Indicates whether the image message is currently active.</li>
 * </ul>
 *
 * Annotations:
 * <ul>
 *   <li>{@code @Entity}: Marks this class as a JPA entity.</li>
 *   <li>{@code @Table(name = "image_messages")}: Specifies the database table name.</li>
 *   <li>Lombok annotations for boilerplate code generation (getters, setters, constructors, equals/hashCode).</li>
 * </ul>
 */
@Entity
@Table(name = "image_messages")
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
