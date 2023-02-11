package com.fdmgroup.helpdeskapi.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "generate_ticket")
    @Setter(AccessLevel.NONE)
    @Column(name = "ticket_id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "resolved", nullable = false)
    private boolean resolved;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "engineer_id")
    private Long engineerId;

    @PrePersist
    private void prePersist() {
        dateCreated = LocalDateTime.now();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

}
