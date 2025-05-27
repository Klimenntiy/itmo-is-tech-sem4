package org.example.Storage.controller;

import org.example.Storage.entities.AccountEventEntity;
import org.example.Storage.entities.ClientEventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.Storage.repositories.AccountEventRepository;
import org.example.Storage.repositories.ClientEventRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Controller", description = "API for managing client and account events")
public class EventController {

    private final ClientEventRepository clientEventRepository;
    private final AccountEventRepository accountEventRepository;

    @Autowired
    public EventController(ClientEventRepository clientEventRepository,
                           AccountEventRepository accountEventRepository) {
        this.clientEventRepository = clientEventRepository;
        this.accountEventRepository = accountEventRepository;
    }

    @GetMapping("/clients")
    @Operation(summary = "Get all client events",
            description = "Retrieves a list of all client-related events")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved client events")
    public List<ClientEventEntity> getAllClientEvents() {
        return clientEventRepository.findAll();
    }

    @GetMapping("/clients/{clientId}")
    @Operation(summary = "Get events by client ID",
            description = "Retrieves events for a specific client")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved client events")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public List<ClientEventEntity> getClientEventsByClientId(
            @Parameter(description = "ID of the client", required = true, example = "123")
            @PathVariable Long clientId) {
        return clientEventRepository.findByClientId(clientId);
    }

    @GetMapping("/accounts")
    @Operation(summary = "Get all account events",
            description = "Retrieves a list of all account-related events")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved account events")
    public List<AccountEventEntity> getAllAccountEvents() {
        return accountEventRepository.findAll();
    }

    @GetMapping("/accounts/{accountId}")
    @Operation(summary = "Get events by account ID",
            description = "Retrieves events for a specific account")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved account events")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public List<AccountEventEntity> getAccountEventsByAccountId(
            @Parameter(description = "ID of the account", required = true, example = "456")
            @PathVariable Long accountId) {
        return accountEventRepository.findByAccountId(accountId);
    }
}