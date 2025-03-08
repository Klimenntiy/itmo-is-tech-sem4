package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDTO;
import org.example.entities.User;
import org.example.enums.Gender;
import org.example.enums.HairColor;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing users.
 */
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Creates a new user if the login is not already taken.
     *
     * @param login     Unique user login.
     * @param name      User's name.
     * @param age       User's age.
     * @param gender    User's gender.
     * @param hairColor User's hair color.
     */
    public void createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        if (userRepository.exists(login)) {
            userRepository.addUser(new User(login, name, age, gender, hairColor));
        }
    }

    /**
     * Adds a friend to the user's friend list if both users exist.
     *
     * @param login       The login of the user adding a friend.
     * @param friendLogin The login of the friend to be added.
     */
    public void addFriend(String login, String friendLogin) {
        if (userRepository.exists(login) || userRepository.exists(friendLogin)) {
            return;
        }
        userRepository.getUser(login).addFriend(friendLogin);
    }

    /**
     * Removes a friend from the user's friend list if both users exist.
     *
     * @param login       The login of the user removing a friend.
     * @param friendLogin The login of the friend to be removed.
     */
    public void removeFriend(String login, String friendLogin) {
        if (userRepository.exists(login) || userRepository.exists(friendLogin)) {
            return;
        }
        userRepository.getUser(login).removeFriend(friendLogin);
    }

    /**
     * Retrieves all users as a list of DTOs.
     *
     * @return A list of UserDTO objects.
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(user -> new UserDTO(
                        user.getLogin(),
                        user.getName(),
                        user.getAge(),
                        user.getGender(),
                        user.getHairColor(),
                        user.getFriends()
                ))
                .collect(Collectors.toList());
    }
}
