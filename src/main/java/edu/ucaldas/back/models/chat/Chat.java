package edu.ucaldas.back.models.chat;

import java.time.LocalDateTime;
import java.util.List;

import edu.ucaldas.back.models.rent.House;
import edu.ucaldas.back.models.user.User;
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
 * Represents a chat conversation between two users regarding a specific house.
 * <p>
 * Each chat is associated with an owner (the house owner), an interested user (the potential buyer/renter),
 * and a house. The chat contains a list of messages exchanged between the users.
 * </p>
 *
 * <p>
 * Fields:
 * <ul>
 *   <li><b>id</b>: Unique identifier for the chat.</li>
 *   <li><b>owner</b>: The user who owns the house.</li>
 *   <li><b>interested</b>: The user interested in the house.</li>
 *   <li><b>house</b>: The house being discussed in the chat.</li>
 *   <li><b>messages</b>: List of messages exchanged in this chat.</li>
 *   <li><b>dateTime</b>: The date and time when the chat was created.</li>
 *   <li><b>isActive</b>: Indicates whether the chat is currently active.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This entity is mapped to the "chats" table in the database.
 * </p>
 */
@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interested_id")
    private User interested;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    private House house;
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<Message> messages;
    private LocalDateTime dateTime;
    private boolean isActive;

    public Chat(User owner, User interested, House house) {
        this.owner = owner;
        this.interested = interested;
        this.house = house;
        this.dateTime = LocalDateTime.now();
        this.isActive = true;
    }

}
