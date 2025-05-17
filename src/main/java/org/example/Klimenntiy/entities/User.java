package org.example.Klimenntiy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Klimenntiy.entities.enums.Gender;
import org.example.Klimenntiy.entities.enums.HairColor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
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

    public User(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
    }

    public boolean isFriend(User otherUser) {
        return this.friends.contains(otherUser.getLogin());
    }

    public void addFriend(String friendLogin) {
        friends.add(friendLogin);
    }

    public void removeFriend(String friendLogin) {
        friends.remove(friendLogin);
    }
}
