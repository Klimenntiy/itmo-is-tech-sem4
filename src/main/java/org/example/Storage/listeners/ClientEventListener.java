package org.example.Storage.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Storage.dto.ClientEventDTO;
import org.example.Storage.services.ClientEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClientEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ClientEventListener.class);

    private final ObjectMapper objectMapper;
    private final ClientEventService clientEventService;

    public ClientEventListener(ObjectMapper objectMapper, ClientEventService clientEventService) {
        this.objectMapper = objectMapper;
        this.clientEventService = clientEventService;
    }

    @RabbitListener(queues = "client-topic")
    public void listen(String message) {
        try {
            ClientEventDTO dto = objectMapper.readValue(message, ClientEventDTO.class);
            clientEventService.handleClientEvent(dto, message);
            logger.info("Successfully processed client event for user ID: {}", dto.getUserId());
        } catch (Exception e) {
            logger.error("Failed to process client event. Message: {}. Error: {}", message, e.getMessage(), e);
        }
    }
}
