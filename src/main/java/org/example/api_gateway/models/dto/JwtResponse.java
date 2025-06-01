package org.example.api_gateway.models.dto;

import lombok.Getter;
import org.example.api_gateway.models.enums.Role;

public class JwtResponse {

    @Getter
    private final String token;
    @Getter
    private final String login;
    private final Role role;

    public JwtResponse(String token, String login, Role role) {
        this.token = token;
        this.login = login;
        this.role = role;
    }

    public String getRole() {
        return role.name();
    }

}
