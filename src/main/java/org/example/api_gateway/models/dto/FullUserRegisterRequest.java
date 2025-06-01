package org.example.api_gateway.models.dto;

import lombok.Data;
import org.example.api_gateway.models.enums.Gender;
import org.example.api_gateway.models.enums.HairColor;
import org.example.api_gateway.models.enums.Role;

@Data
public class FullUserRegisterRequest {
    private String login;
    private String password;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;
    private Role role;

}
