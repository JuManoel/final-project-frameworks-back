package edu.ucaldas.back.models.chat;

import java.time.LocalDateTime;

import edu.ucaldas.back.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
 * Each message is associated with a chat, a sender (user), optional image content,
 * and contains metadata such as timestamp, read status, and active status.
 *
 * Fields:
 * <ul>
 *   <li>id - Unique identifier for the message.</li>
 *   <li>chat - The chat to which this message belongs.</li>
 *   <li>content - The textual content of the message.</li>
 *   <li>sender - The user who sent the message.</li>
 *   <li>dateTime - The date and time when the message was sent.</li>
 *   <li>imageMessage - Optional image associated with the message.</li>
 *   <li>isRead - Indicates if the message has been read.</li>
 *   <li>isActive - Indicates if the message is active (not deleted or archived).</li>
 * </ul>
 *
 * Constructors:
 * <ul>
 *   <li>No-args constructor for JPA.</li>
 *   <li>All-args constructor for full initialization.</li>
 *   <li>Custom constructor to create a message from message data, chat, and sender.</li>
 * </ul>
 */
@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    private Chat chat;
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User sender;
    private LocalDateTime dateTime;
    @OneToOne(fetch = FetchType.EAGER)
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
