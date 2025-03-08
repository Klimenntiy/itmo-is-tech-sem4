package org.example.dto;

import org.example.enums.Gender;
import org.example.enums.HairColor;
import java.util.Set;

/**
 * Data Transfer Object for representing a user.
 */

public record UserDTO(String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friends) {
}
