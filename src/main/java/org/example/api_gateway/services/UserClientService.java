package org.example.api_gateway.services;

import org.example.api_gateway.models.dto.FullUserRegisterRequest;
import org.example.api_gateway.models.dto.UserDTO;
import org.example.api_gateway.models.dto.UserProfileDTO;
import org.example.api_gateway.models.enums.Gender;
import org.example.api_gateway.models.enums.HairColor;
import org.example.api_gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserClientService {

    private final RestTemplate restTemplate;

    @Value("${app.mainapp.url}")
    private String mainAppUrl;

    public UserClientService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders createAuthHeaders(Authentication auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(extractToken(auth));
        return headers;
    }


    private String extractToken(Authentication auth) {
        return (String) auth.getCredentials();
    }

    public void _addFriend(Authentication auth, String friendLogin) {
        String url = mainAppUrl + "/users/" + auth.getName() + "/friends/" + friendLogin;
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(createAuthHeaders(auth)),
                Void.class
        );
    }


    public void _removeFriend(Authentication auth, String friendLogin) {
        String url = mainAppUrl + "/users/" + auth.getName() + "/friends/" + friendLogin;
        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>(createAuthHeaders(auth)),
                Void.class
        );
    }

    public void _registerFullUser(FullUserRegisterRequest request, String token, UserProfileDTO userProfileDTO1) {
        String profileUrl = mainAppUrl + "/users/create";


        restTemplate.exchange(
                profileUrl,
                HttpMethod.POST,
                new HttpEntity<>(userProfileDTO1, createHeaders(token)),
                Void.class
        );
    }


    public UserDTO[] _getUsers(String token, Gender gender, HairColor hairColor, String url , HttpEntity<Void> entity ) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserDTO[].class
        ).getBody();
    }

    public UserDTO _getUserById(String token, Long id, String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                UserDTO.class
        ).getBody();
    }
}
