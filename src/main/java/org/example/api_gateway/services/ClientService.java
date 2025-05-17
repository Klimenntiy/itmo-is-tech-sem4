package org.example.api_gateway.services;

import org.example.api_gateway.models.dto.AccountDTO;
import org.example.api_gateway.models.dto.AccountOperationRequest;
import org.example.api_gateway.models.dto.UserDTO;
import org.example.api_gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${app.mainapp.url}")
    private String mainAppUrl;

    public ClientService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
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
        String url = mainAppUrl + "/users/login/" + auth.getName();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAuthHeaders(auth)),
                UserDTO.class
        ).getBody();
    }

    public AccountDTO[] getMyAccounts(Authentication auth) {
        Long userId = getUserIdFromAuth(auth);
        String url = mainAppUrl + "/accounts/user/id/" + userId;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAuthHeaders(auth)),
                AccountDTO[].class
        ).getBody();
    }

    public AccountDTO getAccountDetails(Authentication auth, Long accountId) {
        String url = mainAppUrl + "/accounts/" + accountId;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAuthHeaders(auth)),
                AccountDTO.class
        ).getBody();
    }

    public void addFriend(Authentication auth, String friendLogin) {
        String url = mainAppUrl + "/users/" + auth.getName() + "/friends/" + friendLogin;
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(createAuthHeaders(auth)),
                Void.class
        );
    }

    public void removeFriend(Authentication auth, String friendLogin) {
        String url = mainAppUrl + "/users/" + auth.getName() + "/friends/" + friendLogin;
        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>(createAuthHeaders(auth)),
                Void.class
        );
    }

    public void performOperation(Authentication auth, Long accountId, AccountOperationRequest request) {
        if (!accountId.equals(request.getAccountId())) {
            throw new IllegalArgumentException("Account ID in path and body must match");
        }

        String url = mainAppUrl + "/accounts/" + accountId + "/operations";
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request, createAuthHeaders(auth)),
                Void.class
        );
    }

}
