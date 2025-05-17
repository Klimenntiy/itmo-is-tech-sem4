package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.Results.Exceptions.AccountNotFoundException;
import org.example.dto.UserDTO;
import org.example.entities.User;
import org.example.entities.enums.Gender;
import org.example.entities.enums.HairColor;
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
     * @throws AccountNotFoundException if the user already exists.
     */
    public void createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        if (userRepository.exists(login)) {
            throw new AccountNotFoundException("User already exists.");
        }
        User newUser = new User(login, name, age, gender, hairColor);
        userRepository.addUser(newUser);
    }

    /**
     * Adds a friend to the user's friend list if both users exist.
     *
     * @param login       The login of the user adding a friend.
     * @param friendLogin The login of the friend to be added.
     * @throws AccountNotFoundException if one or both users are not found.
     */
    public void addFriend(String login, String friendLogin) {
        User user = userRepository.getUser(login);
        User friend = userRepository.getUser(friendLogin);

        if (user == null || friend == null) {
            throw new AccountNotFoundException("One or both users not found.");
        }

        user.addFriend(friendLogin);
        friend.addFriend(login);

        userRepository.addUser(user);
        userRepository.addUser(friend);
    }

    /**
     * Removes a friend from the user's friend list if both users exist.
     *
     * @param login       The login of the user removing a friend.
     * @param friendLogin The login of the friend to be removed.
     * @throws AccountNotFoundException if one or both users are not found.
     */
    public void removeFriend(String login, String friendLogin) {
        User user = userRepository.getUser(login);
        User friend = userRepository.getUser(friendLogin);

        if (user == null || friend == null) {
            throw new AccountNotFoundException("One or both users not found.");
        }

        user.removeFriend(friendLogin);
        friend.removeFriend(login);

        userRepository.addUser(user);
        userRepository.addUser(friend);
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
