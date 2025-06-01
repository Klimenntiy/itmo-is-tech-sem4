package org.example.Storage.services;

import org.example.Storage.dto.ClientEventDTO;
import org.example.Storage.entities.ClientEventEntity;
import org.example.Storage.repositories.ClientEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientEventService {

    private final ClientEventRepository clientEventRepository;

    public ClientEventService(ClientEventRepository clientEventRepository) {
        this.clientEventRepository = clientEventRepository;
    }

    public void handleClientEvent(ClientEventDTO dto, String rawMessage) {
        ClientEventEntity entity = new ClientEventEntity();
        entity.setClientId(dto.getUserId());
        entity.setEventTimestamp(LocalDateTime.now());
        entity.setEventData(rawMessage);
        clientEventRepository.save(entity);
    }
}
