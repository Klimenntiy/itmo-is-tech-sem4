package org.example.api_gateway.services;

import org.example.api_gateway.models.dto.AccountDTO;
import org.example.api_gateway.models.dto.AccountOperationRequest;
import org.example.api_gateway.models.dto.UserDTO;
import org.example.api_gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final AccountClientService accountClientService;
    private final UserClientService userClientService;

    @Value("${app.mainapp.url}")
    private String mainAppUrl;

    public ClientService(RestTemplate restTemplate, UserRepository userRepository, AccountClientService accountClientService, UserClientService userClientService) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.accountClientService = accountClientService;
        this.userClientService = userClientService;
    }

    private HttpHeaders createAuthHeaders(Authentication auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(extractToken(auth));
        return headers;
    }

    private String extractToken(Authentication auth) {
        return (String) auth.getCredentials();
    }

    private Long getUserIdFromAuth(Authentication auth) {
        return userRepository.findByLogin(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    public UserDTO getMyInfo(Authentication auth) {
       return accountClientService._getMyInfo(auth);
    }

    public AccountDTO[] getMyAccounts(Authentication auth) {
        return accountClientService._getMyAccounts(auth);
    }

    public AccountDTO getAccountDetails(Authentication auth, Long accountId) {
            return accountClientService._getAccountDetails(auth, accountId);
    }

    public void addFriend(Authentication auth, String friendLogin) {
            userClientService._addFriend(auth, friendLogin);
    }

    public void removeFriend(Authentication auth, String friendLogin) {
            userClientService._removeFriend(auth, friendLogin);
    }

    public void performOperation(Authentication auth, Long accountId, AccountOperationRequest request) {
        if (!accountId.equals(request.getAccountId())) {
            throw new IllegalArgumentException("Account ID in path and body must match");
        }
        accountClientService._performOperation(auth, accountId, request);
    }

}