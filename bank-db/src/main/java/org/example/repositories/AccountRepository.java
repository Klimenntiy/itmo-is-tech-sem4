package org.example.repositories;

import org.example.HibernateUtil;
import org.example.entities.Account;
import org.example.entities.TransactionHistory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Repository class for interacting with the Account and TransactionHistory entities.
 * Provides methods to add, get, and update accounts, as well as saving transaction histories.
 */
public class AccountRepository {

    /**
     * Adds a new account to the database.
     *
     * @param account the account to be added
     */
    public void addAccount(Account account) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(account);
            transaction.commit();
        }
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the account to retrieve
     * @return the account with the specified ID
     */
    public Account getAccount(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Account a LEFT JOIN FETCH a.transactions WHERE a.id = :id", Account.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    /**
     * Retrieves all accounts associated with a specific user.
     *
     * @param userLogin the login of the user whose accounts to retrieve
     * @return a list of accounts associated with the given user login
     */
    public List<Account> getAccountsByUser(String userLogin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Account WHERE ownerLogin = :login", Account.class)
                    .setParameter("login", userLogin)
                    .list();
        }
    }

    /**
     * Updates an existing account in the database.
     *
     * @param account the account to be updated
     */
    public void saveAccount(Account account) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(account);
            transaction.commit();
        }
    }

    /**
     * Saves a transaction history record to the database.
     *
     * @param transaction the transaction history to be saved
     */
    public void saveTransactionHistory(TransactionHistory transaction) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(transaction);
            tx.commit();
        }
    }
}
