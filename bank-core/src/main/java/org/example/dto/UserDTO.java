package org.example.dto;

import org.example.entities.enums.Gender;
import org.example.entities.enums.HairColor;
import java.util.Set;

/**
 * Data Transfer Object for representing a user.
 */
public record UserDTO(String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friends) {
}
