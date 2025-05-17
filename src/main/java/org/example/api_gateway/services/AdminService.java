package org.example.api_gateway.services;

import org.example.api_gateway.models.User;
import org.example.api_gateway.models.dto.*;
import org.example.api_gateway.models.enums.Gender;
import org.example.api_gateway.models.enums.HairColor;
import org.example.api_gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Value("${app.mainapp.url}")
    private String mainAppUrl;

    public AdminService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public UserDTO registerFullUser(FullUserRegisterRequest request, String token) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setHairColor(request.getHairColor());

        user = userRepository.save(user);


        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .hairColor(user.getHairColor())
                .build();


        String profileUrl = mainAppUrl + "/users/create";
        UserProfileDTO profileDTO = UserProfileDTO.builder()
                .userId(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .hairColor(user.getHairColor())
                .build();

        ResponseEntity<Void> profileResponse = restTemplate.exchange(
                profileUrl,
                HttpMethod.POST,
                new HttpEntity<>(profileDTO, createHeaders(token)),
                Void.class
        );

        if (!profileResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Ошибка при создании профиля пользователя");
        }

        return userDTO;
    }



    public UserDTO[] getUsers(String token, Gender gender, HairColor hairColor) {
        StringBuilder urlBuilder = new StringBuilder(mainAppUrl).append("/users?");

        boolean firstParam = true;
        if (gender != null) {
            urlBuilder.append("gender=").append(gender);
            firstParam = false;
        }
        if (hairColor != null) {
            if (!firstParam) {
                urlBuilder.append("&");
            }
            urlBuilder.append("hairColor=").append(hairColor);
        }

        String url = urlBuilder.toString();

        HttpEntity<Void> entity = new HttpEntity<>(createHeaders(token));
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserDTO[].class
        ).getBody();
    }

    public UserDTO getUserById(String token, Long id) {
        String url = mainAppUrl + "/users/" + id;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                UserDTO.class
        ).getBody();
    }

    public AccountDTO[] getAllAccounts(String token) {
        String url = mainAppUrl + "/accounts/all";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                AccountDTO[].class
        ).getBody();
    }

    public AccountDTO[] getUserAccounts(String token, Long userId) {
        String url = mainAppUrl + "/accounts/user/id/" + userId;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                AccountDTO[].class
        ).getBody();
    }

    public AccountDTO getAccountDetails(String token, Long accountId) {
        String url = mainAppUrl + "/accounts/" + accountId;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                AccountDTO.class
        ).getBody();
    }

    public String createAccount(String token, Long ownerId) {
        String url = mainAppUrl + "/accounts/create/" + ownerId;

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(null, createHeaders(token)),
                String.class
        ).getBody();
    }



    public void logout() {

    }

}
