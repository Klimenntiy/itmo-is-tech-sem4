package org.example.repositories;

import lombok.Getter;
import org.example.entities.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository for managing user accounts.
 */
@Getter
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    /**
     * Adds a new user to the repository.
     *
     * @param user The user to add
     */
    public void addUser(User user) {
        users.put(user.getLogin(), user);
    }

    /**
     * Retrieves a user by login.
     *
     * @param login The login of the user
     * @return The user if found, otherwise null
     */
    public User getUser(String login) {
        return users.get(login);
    }

    /**
     * Checks if a user exists in the repository.
     *
     * @param login The login of the user
     * @return true if the user exists, otherwise false
     */
    public boolean exists(String login) {
        return users.containsKey(login);
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return A collection of all users
     */
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
