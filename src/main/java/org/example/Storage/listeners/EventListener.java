package org.example.Storage.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Storage.dto.AccountEventDTO;
import org.example.Storage.dto.ClientEventDTO;
import org.example.Storage.entities.AccountEventEntity;
import org.example.Storage.entities.ClientEventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.example.Storage.repositories.AccountEventRepository;
import org.example.Storage.repositories.ClientEventRepository;

import java.time.LocalDateTime;

@Component
public class EventListener {

    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);

    private final ObjectMapper objectMapper;
    private final ClientEventRepository clientEventRepository;
    private final AccountEventRepository accountEventRepository;

    public EventListener(ObjectMapper objectMapper,
                         ClientEventRepository clientEventRepository,
                         AccountEventRepository accountEventRepository) {
        this.objectMapper = objectMapper;
        this.clientEventRepository = clientEventRepository;
        this.accountEventRepository = accountEventRepository;
    }

    @RabbitListener(queues = "client-topic")
    public void listenClientEvent(String message) {
        try {
            ClientEventDTO dto = objectMapper.readValue(message, ClientEventDTO.class);
            ClientEventEntity entity = new ClientEventEntity();
            entity.setClientId(dto.getUserId());
            entity.setEventTimestamp(LocalDateTime.now());
            entity.setEventData(message);
            clientEventRepository.save(entity);
            logger.info("Successfully processed client event for user ID: {}", dto.getUserId());
        } catch (Exception e) {
            logger.error("Failed to process client event. Message: {}. Error: {}", message, e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "account-topic")
    public void listenAccountEvent(String message) {
        try {
            AccountEventDTO dto = objectMapper.readValue(message, AccountEventDTO.class);
            AccountEventEntity entity = new AccountEventEntity();
            entity.setAccountId(dto.getOwnerId());
            entity.setEventTimestamp(LocalDateTime.now());
            entity.setEventData(message);
            accountEventRepository.save(entity);
            logger.info("Successfully processed account event for account ID: {}", dto.getOwnerId());
        } catch (Exception e) {
            logger.error("Failed to process account event. Message: {}. Error: {}", message, e.getMessage(), e);
        }
    }
}