package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.Results.SuccessResult;
import org.example.dto.AccountDTO;
import org.example.entities.Account;
import org.example.Results.Exceptions.AccountNotFoundException;
import org.example.Results.Exceptions.InsufficientFundsException;
import org.example.Results.Exceptions.InvalidAmountException;
import org.example.repositories.AccountRepository;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing bank accounts.
 */
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new bank account for a user.
     *
     * @param login User's login
     */
    public void createAccount(String login) {
        if (userRepository.exists(login)) {
            throw new AccountNotFoundException("User not found.");
        }
        accountRepository.addAccount(new Account(login));
    }

    /**
     * Deposits money into an account.
     *
     * @param accountId Account ID
     * @param amount    Deposit amount
     */
    public SuccessResult depositMoney(String accountId, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }

        Account account = accountRepository.getAccount(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        account.deposit(amount);
        accountRepository.saveAccount(account);

        return new SuccessResult("Deposit successful! New balance: " + account.getBalance());
    }


    /**
     * Withdraws money from an account if there are sufficient funds.
     *
     * @param accountId Account ID
     * @param amount    Withdrawal amount
     */
    public SuccessResult withdrawMoney(String accountId, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        Account account = accountRepository.getAccount(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        if (amount > account.getBalance()) {
            throw new InsufficientFundsException("Insufficient funds!");
        }
        account.withdraw(amount);
        accountRepository.saveAccount(account);
        return new SuccessResult("Withdrawal successful! New balance: " + account.getBalance());
    }


    /**
     * Displays the account balance.
     *
     * @param accountId Account ID
     */
    public void showBalance(String accountId) {
        Account account = accountRepository.getAccount(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        System.out.println("Balance for account " + accountId + ": " + account.getBalance());
    }

    /**
     * Retrieves all accounts associated with a user.
     *
     * @param login User's login
     * @return List of AccountDTO objects
     */
    public List<AccountDTO> getUserAccounts(String login) {
        if (userRepository.exists(login)) {
            throw new AccountNotFoundException("User not found.");
        }
        return accountRepository.getAccountsByUser(login).stream()
                .map(account -> new AccountDTO(account.getId(), account.getOwnerLogin(), account.getBalance()))
                .collect(Collectors.toList());
    }
}
