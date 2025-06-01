package org.example.Storage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "account_events")
public class AccountEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long accountId;

    @Setter
    private LocalDateTime eventTimestamp;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String eventData;

    public AccountEventEntity() {}

    public AccountEventEntity(Long accountId, LocalDateTime eventTimestamp, String eventData) {
        this.accountId = accountId;
        this.eventTimestamp = eventTimestamp;
        this.eventData = eventData;
    }


}
