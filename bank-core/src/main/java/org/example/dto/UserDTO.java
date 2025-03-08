package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.enums.Gender;
import org.example.enums.HairColor;
import java.util.Set;

/**
 * Data Transfer Object for representing a user.
 */
@Getter
@RequiredArgsConstructor
public class UserDTO {
    private final String login;
    private final String name;
    private final int age;
    private final Gender gender;
    private final HairColor hairColor;
    private final Set<String> friends;
}
