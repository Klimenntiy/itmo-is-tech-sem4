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
public class AccountClientService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${app.mainapp.url}")
    private String mainAppUrl;

    public AccountClientService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    private HttpHeaders createAuthHeaders(Authentication auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(extractToken(auth));
        return headers;
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private Long getUserIdFromAuth(Authentication auth) {
        return userRepository.findByLogin(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    private String extractToken(Authentication auth) {
        return (String) auth.getCredentials();
    }

    public UserDTO _getMyInfo(Authentication auth) {
        String url = mainAppUrl + "/users/login/" + auth.getName();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAuthHeaders(auth)),
                UserDTO.class
        ).getBody();
    }

    public AccountDTO[] _getMyAccounts(Authentication auth) {
        Long userId = getUserIdFromAuth(auth);
        String url = mainAppUrl + "/accounts/user/id/" + userId;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAuthHeaders(auth)),
                AccountDTO[].class

        ).getBody();
    }

    public AccountDTO _getAccountDetails(Authentication auth, Long accountId) {
        String url = mainAppUrl + "/accounts/" + accountId;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAuthHeaders(auth)),
                AccountDTO.class
        ).getBody();
    }

    public void _performOperation(Authentication auth, Long accountId, AccountOperationRequest request) {

        String url = mainAppUrl + "/accounts/" + accountId + "/operations";
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request, createAuthHeaders(auth)),
                Void.class
        );
    }


    public AccountDTO[] _getAllAccounts(String token, String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                AccountDTO[].class
        ).getBody();
    }

    public AccountDTO[] _getUserAccounts(String token, Long userId, String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                AccountDTO[].class
        ).getBody();
    }


    public AccountDTO _getAccountDetails(String token, Long accountId, String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                AccountDTO.class
        ).getBody();
    }

    public String _createAccount(String token, Long ownerId, String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(null, createHeaders(token)),
                String.class
        ).getBody();
    }

}
