package org.example.Storage.services;

import org.example.Storage.dto.AccountEventDTO;
import org.example.Storage.entities.AccountEventEntity;
import org.example.Storage.repositories.AccountEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountEventService {

    private final AccountEventRepository accountEventRepository;

    public AccountEventService(AccountEventRepository accountEventRepository) {
        this.accountEventRepository = accountEventRepository;
    }

    public void handleAccountEvent(AccountEventDTO dto, String rawMessage) {
        AccountEventEntity entity = new AccountEventEntity();
        entity.setAccountId(dto.getOwnerId());
        entity.setEventTimestamp(LocalDateTime.now());
        entity.setEventData(rawMessage);
        accountEventRepository.save(entity);
    }
}
