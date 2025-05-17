package org.example.Klimenntiy.mappers;

import org.example.Klimenntiy.dto.UserDTO;
import org.example.Klimenntiy.entities.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getHairColor(),
                user.getFriends()
        );
    }
}
