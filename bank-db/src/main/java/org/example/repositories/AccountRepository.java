package org.example.repositories;

import lombok.Getter;
import org.example.entities.Account;

import java.util.*;

/**
 * Repository for managing bank accounts.
 * <p>
 * This class provides basic CRUD operations for managing accounts,
 * including adding, retrieving, updating, and checking account existence.
 * It uses an in-memory storage (HashMap) to manage accounts.
 */
@Getter
public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    /**
     * Adds a new account to the repository.
     * If an account with the same ID already exists, it will be overwritten.
     *
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    /**
     * Retrieves an account by its unique identifier.
     *
     * @param id The account ID.
     * @return The account if found, otherwise {@code null}.
     */
    public Account getAccount(String id) {
        return accounts.get(id);
    }

    /**
     * Retrieves all accounts associated with a specific user.
     *
     * @param userLogin The user's login identifier.
     * @return A list of accounts owned by the specified user.
     *         If the user has no accounts, an empty list is returned.
     */
    public List<Account> getAccountsByUser(String userLogin) {
        List<Account> userAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getOwnerLogin().equals(userLogin)) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }

    /**
     * Updates an existing account in the repository.
     * If the account does not exist, this method does nothing.
     *
     * @param account The account to update.
     */
    public void saveAccount(Account account) {
        if (accounts.containsKey(account.getId())) {
            accounts.put(account.getId(), account);
        }
    }
}
