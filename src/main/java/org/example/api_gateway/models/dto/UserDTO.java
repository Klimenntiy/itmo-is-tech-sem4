package org.example.api_gateway.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api_gateway.models.enums.Gender;
import org.example.api_gateway.models.enums.HairColor;
import org.example.api_gateway.models.enums.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;
    private Set<String> friends;
    private Role role;
}
