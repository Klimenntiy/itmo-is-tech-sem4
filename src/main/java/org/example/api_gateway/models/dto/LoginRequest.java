package org.example.api_gateway.models.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String login;

    private String password;

}
