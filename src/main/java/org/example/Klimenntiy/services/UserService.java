package org.example.Klimenntiy.services;

import org.example.Klimenntiy.dto.UserDTO;
import org.example.Klimenntiy.entities.User;
import org.example.Klimenntiy.exceptions.UserNotFoundException;
import org.example.Klimenntiy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDTO userDTO) {
        if (userRepository.existsByLogin(userDTO.login())) {
            throw new UserNotFoundException("User already exists.");
        }

        User newUser = new User(
                userDTO.login(),
                userDTO.name(),
                userDTO.age(),
                userDTO.gender(),
                userDTO.hairColor()
        );

        userRepository.save(newUser);
    }

    public void addFriend(String login, String friendLogin) {
        User user = getUserByLogin(login);
        User friend = getUserByLogin(friendLogin);

        user.addFriend(friendLogin);
        friend.addFriend(login);

        userRepository.save(user);
        userRepository.save(friend);
    }

    public void removeFriend(String login, String friendLogin) {
        User user = getUserByLogin(login);
        User friend = getUserByLogin(friendLogin);

        user.removeFriend(friendLogin);
        friend.removeFriend(login);

        userRepository.save(user);
        userRepository.save(friend);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getFriendsByUserId(Long userId) {
        User user = getUserById(userId);

        return user.getFriends().stream()
                .map(friendLogin -> convertToDTO(getUserByLogin(friendLogin)))
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersWithFilters(String hairColor, String gender) {
        List<User> users = userRepository.findAll();

        if (hairColor != null) {
            users = users.stream()
                    .filter(user -> user.getHairColor().name().equalsIgnoreCase(hairColor))
                    .collect(Collectors.toList());
        }

        if (gender != null) {
            users = users.stream()
                    .filter(user -> user.getGender().name().equalsIgnoreCase(gender))
                    .collect(Collectors.toList());
        }

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    private User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    private UserDTO convertToDTO(User user) {
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
