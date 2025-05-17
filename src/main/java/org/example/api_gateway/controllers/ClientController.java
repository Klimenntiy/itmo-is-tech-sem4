package org.example.api_gateway.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.api_gateway.models.dto.AccountOperationRequest;
import org.example.api_gateway.models.dto.AccountDTO;
import org.example.api_gateway.models.dto.UserDTO;
import org.example.api_gateway.models.enums.OperationType;
import org.example.api_gateway.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Client API", description = "Endpoints for client operations")
@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Get current user info")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyInfo(Authentication auth) {
        return ResponseEntity.ok(clientService.getMyInfo(auth));
    }

    @Operation(summary = "Get all accounts of the current user")
    @GetMapping("/accounts")
    public ResponseEntity<AccountDTO[]> getMyAccounts(Authentication auth) {
        return ResponseEntity.ok(clientService.getMyAccounts(auth));
    }

    @Operation(summary = "Get details of a specific account by ID")
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccountDetails(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(clientService.getAccountDetails(auth, id));
    }

    @Operation(summary = "Add a friend by login")
    @PostMapping("/friends")
    public ResponseEntity<Void> addFriend(@RequestParam String friendLogin, Authentication auth) {
        clientService.addFriend(auth, friendLogin);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove a friend by login")
    @DeleteMapping("/friends")
    public ResponseEntity<Void> removeFriend(@RequestParam String friendLogin, Authentication auth) {
        clientService.removeFriend(auth, friendLogin);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Perform an operation on an account via params")
    @PostMapping("/accounts/{accountId}/operations")
    public ResponseEntity<Void> performOperationViaParams(
            @PathVariable Long accountId,
            @RequestParam double amount,
            @RequestParam(required = false) Long targetAccountId,
            @RequestParam OperationType operationType,
            Authentication auth
    ) {
        AccountOperationRequest request = new AccountOperationRequest();
        request.setAccountId(accountId);
        request.setAmount(amount);
        request.setTargetAccountId(targetAccountId);
        request.setOperationType(operationType.name());

        clientService.performOperation(auth, accountId, request);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Logout current user")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

}
