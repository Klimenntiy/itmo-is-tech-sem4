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
 * Service class for handling bank account operations.
 */
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new bank account for the specified user.
     *
     * @param login User's login identifier.
     * @throws AccountNotFoundException if the user does not exist.
     */
    public void createAccount(String login) {
        if (!userRepository.exists(login)) {
            throw new AccountNotFoundException("User not found.");
        }
        accountRepository.addAccount(new Account(login));
    }

    /**
     * Deposits a specified amount into an account.
     *
     * @param accountId The account ID.
     * @param amount    The amount to deposit.
     * @return A success result with the updated balance.
     * @throws InvalidAmountException if the deposit amount is non-positive.
     * @throws AccountNotFoundException if the account does not exist.
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
     * Withdraws a specified amount from an account if sufficient funds are available.
     *
     * @param accountId The account ID.
     * @param amount    The amount to withdraw.
     * @return A success result with the updated balance.
     * @throws InvalidAmountException if the withdrawal amount is non-positive.
     * @throws AccountNotFoundException if the account does not exist.
     * @throws InsufficientFundsException if the account lacks sufficient funds.
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
     * Retrieves and prints the balance of the specified account.
     *
     * @param accountId The account ID.
     * @throws AccountNotFoundException if the account does not exist.
     */
    public void showBalance(String accountId) {
        Account account = accountRepository.getAccount(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        System.out.println("Balance for account " + accountId + ": " + account.getBalance());
    }

    /**
     * Retrieves all accounts associated with a specific user.
     *
     * @param login The user's login identifier.
     * @return A list of AccountDTO objects.
     * @throws AccountNotFoundException if the user does not exist.
     */
    public List<AccountDTO> getUserAccounts(String login) {
        if (!userRepository.exists(login)) {
            throw new AccountNotFoundException("User not found.");
        }

        return accountRepository.getAccountsByUser(login).stream()
                .map(account -> new AccountDTO(account.getId(), account.getOwnerLogin(), account.getBalance()))
                .collect(Collectors.toList());
    }
}
