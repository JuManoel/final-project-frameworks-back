package edu.ucaldas.back.models.chat;

import java.time.LocalDateTime;

import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a message entity in a chat system.
 * This class is mapped to the "message" table in the database.
 * It contains information about the message content, sender, associated chat,
 * timestamp, and additional metadata such as read status and activity status.
 * 
 * An instance of this class can also be associated with an image message.
 * 
 * An additional constructor is provided to create a Message instance
 * using a MessageData object, a Chat, and a User.
 * 
 * Annotations:
 * - @Entity: Marks this class as a JPA entity.
 * - @Table: Specifies the table name in the database.
 * - @Getter, @Setter: Lombok annotations to generate getter and setter methods.
 * - @NoArgsConstructor, @AllArgsConstructor: Lombok annotations to generate constructors.
 * - @EqualsAndHashCode: Lombok annotation to generate equals and hashCode methods based on the "id" field.
 * 
 * Fields:
 * - id: Unique identifier for the message (primary key).
 * - chat: The chat to which this message belongs (many-to-one relationship).
 * - content: The textual content of the message.
 * - sender: The user who sent the message (many-to-one relationship).
 * - dateTime: The timestamp when the message was created.
 * - imageMessage: An optional image associated with the message (one-to-one relationship).
 * - isRead: Indicates whether the message has been read.
 * - isActive: Indicates whether the message is active.
 * 
 * Relationships:
 * - @ManyToOne: Used for the "chat" and "sender" fields to define many-to-one relationships.
 * - @OneToOne: Used for the "imageMessage" field to define a one-to-one relationship.
 * - @JoinColumn: Specifies the foreign key columns for the relationships.
 */
@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;
    private LocalDateTime dateTime;
    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageMessage imageMessage;
    private boolean isRead;
    private boolean isActive;

    public Message(MessageData messageData, Chat chat, User sender) {
        this.chat = chat;
        this.content = messageData.content();
        this.sender = sender;
        this.dateTime = LocalDateTime.now();
        this.isRead = false;
        this.isActive = true;
    }

}
