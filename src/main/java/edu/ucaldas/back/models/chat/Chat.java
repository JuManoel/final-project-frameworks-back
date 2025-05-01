package edu.ucaldas.back.models.chat;

import java.time.LocalDateTime;
import java.util.List;

import edu.ucaldas.back.models.rent.House;
import edu.ucaldas.back.models.user.User;
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
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "interested_id")
    private User interested;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    @OneToMany(mappedBy = "chat")
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
