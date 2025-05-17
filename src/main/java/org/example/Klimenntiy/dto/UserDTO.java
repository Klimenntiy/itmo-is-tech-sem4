package org.example.Klimenntiy.dto;

import org.example.Klimenntiy.entities.enums.Gender;
import org.example.Klimenntiy.entities.enums.HairColor;

import java.util.Set;

public record UserDTO(Long userId, String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friends) {
}
