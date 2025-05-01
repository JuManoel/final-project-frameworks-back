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
