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
    private final UserClientService userClientService;
    private final AccountClientService accountClientService;

    @Value("${app.mainapp.url}")
    private String mainAppUrl;

    public AdminService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        RestTemplate restTemplate, UserClientService userClientService, AccountClientService accountClientService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.userClientService = userClientService;
        this.accountClientService = accountClientService;
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

        UserProfileDTO profileDTO = UserProfileDTO.builder()
                .userId(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .hairColor(user.getHairColor())
                .build();

        userClientService._registerFullUser(request, token, profileDTO);

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
        return userClientService._getUsers(token, gender, hairColor, url, entity);
    }

    public UserDTO getUserById(String token, Long id) {
        String url = mainAppUrl + "/users/" + id;
        return userClientService._getUserById(token, id, url);
    }


    public AccountDTO[] getAllAccounts(String token) {
        String url = mainAppUrl + "/accounts/all";
        return accountClientService._getAllAccounts(token, url);
    }

    public AccountDTO[] getUserAccounts(String token, Long userId) {
        String url = mainAppUrl + "/accounts/user/id/" + userId;
        return accountClientService._getUserAccounts(token, userId, url);
    }

    public AccountDTO getAccountDetails(String token, Long accountId) {
        String url = mainAppUrl + "/accounts/" + accountId;
        return accountClientService._getAccountDetails(token, accountId, url);
    }

    public String createAccount(String token, Long ownerId) {
        String url = mainAppUrl + "/accounts/create/" + ownerId;
        return accountClientService._createAccount(token, ownerId, url);
    }

}
