package org.example.Storage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "client_events")
public class ClientEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long clientId;

    @Setter
    private LocalDateTime eventTimestamp;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String eventData;


    public ClientEventEntity() {}

    public ClientEventEntity(Long clientId, LocalDateTime eventTimestamp, String eventData) {
        this.clientId = clientId;
        this.eventTimestamp = eventTimestamp;
        this.eventData = eventData;
    }


}
