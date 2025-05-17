package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.enums.Gender;
import org.example.entities.enums.HairColor;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user entity.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HairColor hairColor;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_login"))
    @Column(name = "friend_login")
    private Set<String> friends = new HashSet<>();

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
     * Checks if the given user is a friend.
     *
     * @param otherUser The user to check friendship with.
     * @return {@code true} if the given user is a friend, otherwise {@code false}.
     */
    public boolean isFriend(User otherUser) {
        return this.friends.contains(otherUser.getLogin());
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
     * Removes a friend from the user's friend list.
     *
     * @param friendLogin The login of the friend to remove.
     */
    public void removeFriend(String friendLogin) {
        friends.remove(friendLogin);
    }
}
