package org.example.Storage.dto;

import lombok.*;
import org.example.Storage.entities.enums.Gender;
import org.example.Storage.entities.enums.HairColor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientEventDTO {
    private Long userId;
    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;

}
