package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.Gender;
import org.example.enums.HairColor;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user in the system.
 */
@Getter
@Setter
public class User {
    @Getter
    private final String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;
    private final Set<String> friends = new HashSet<>();

    /**
     * Constructs a new user.
     *
     * @param login     The unique login of the user.
     * @param name      The name of the user.
     * @param age       The age of the user.
     * @param gender    The gender of the user.
     * @param hairColor The hair color of the user.
     */
    public User(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friendLogin The login of the friend to add.
     */
    public void addFriend(String friendLogin) {
        friends.add(friendLogin);
    }

    /**
     * Checks if another user is a friend of the current user.
     *
     * @param otherUser The other user to check.
     * @return true if the user is a friend, false otherwise.
     */
    public boolean isFriend(User otherUser) {
        return friends.contains(otherUser.getLogin());
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param friendLogin The login of the friend to remove.
     */
    public void removeFriend(String friendLogin) {
        friends.remove(friendLogin);
    }
}
