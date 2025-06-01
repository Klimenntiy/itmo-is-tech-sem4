package org.example.Storage.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Storage.dto.AccountEventDTO;
import org.example.Storage.services.AccountEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AccountEventListener.class);

    private final ObjectMapper objectMapper;
    private final AccountEventService accountEventService;

    public AccountEventListener(ObjectMapper objectMapper, AccountEventService accountEventService) {
        this.objectMapper = objectMapper;
        this.accountEventService = accountEventService;
    }

    @RabbitListener(queues = "account-topic")
    public void listen(String message) {
        try {
            AccountEventDTO dto = objectMapper.readValue(message, AccountEventDTO.class);
            accountEventService.handleAccountEvent(dto, message);
            logger.info("Successfully processed account event for account ID: {}", dto.getOwnerId());
        } catch (Exception e) {
            logger.error("Failed to process account event. Message: {}. Error: {}", message, e.getMessage(), e);
        }
    }
}
