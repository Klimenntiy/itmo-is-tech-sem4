package org.example.api_gateway.models.dto;

import lombok.*;
import org.example.api_gateway.models.enums.Gender;
import org.example.api_gateway.models.enums.HairColor;

import java.util.Set;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long userId;
    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;
    private Set<String> friends;
}
