package org.example.repositories;

import org.example.HibernateUtil;
import org.example.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Repository class for interacting with the User entity.
 * Provides methods to add, retrieve, and check users in the database.
 */
public class UserRepository {

    /**
     * Adds or updates a user in the database.
     * If the user already exists, their information is updated.
     * Otherwise, a new user is added.
     *
     * @param user the user to be added or updated
     */
    public void addUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User existingUser = session.get(User.class, user.getLogin());
            if (existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setAge(user.getAge());
                existingUser.setGender(user.getGender());
                existingUser.setHairColor(user.getHairColor());
                existingUser.getFriends().clear();
                existingUser.getFriends().addAll(user.getFriends());
                session.merge(existingUser);
            } else {
                session.persist(user);
            }

            transaction.commit();
        }
    }

    /**
     * Retrieves a user by their login.
     *
     * @param login the login of the user to retrieve
     * @return the user with the specified login
     */
    public User getUser(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, login);
        }
    }

    /**
     * Checks if a user with the given login exists in the database.
     *
     * @param login the login of the user to check
     * @return true if the user exists, false otherwise
     */
    public boolean exists(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, login) != null;
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User u LEFT JOIN FETCH u.friends", User.class).list();
        }
    }
}
