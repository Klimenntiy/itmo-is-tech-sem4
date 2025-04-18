package org.example.Klimenntiy.services;

import org.example.Klimenntiy.dto.AccountDTO;
import org.example.Klimenntiy.entities.Account;
import org.example.Klimenntiy.exceptions.AccountNotFoundException;
import org.example.Klimenntiy.exceptions.InsufficientFundsException;
import org.example.Klimenntiy.exceptions.InvalidAmountException;
import org.example.Klimenntiy.repository.AccountRepository;
import org.example.Klimenntiy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public void createAccount(String login) {
        if (!userRepository.existsByLogin(login)) {
            throw new AccountNotFoundException("User not found.");
        }
        accountRepository.save(new Account(login));
    }

    public void depositMoney(Long accountId, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        account.deposit(amount);
        accountRepository.save(account);
    }

    public void withdrawMoney(Long accountId, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        if (amount > account.getBalance()) {
            throw new InsufficientFundsException("Insufficient funds!");
        }

        account.withdraw(amount);
        accountRepository.save(account);
    }

    public double showBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));
        return account.getBalance();
    }

    public List<AccountDTO> getUserAccounts(String login) {
        if (!userRepository.existsByLogin(login)) {
            throw new AccountNotFoundException("User not found.");
        }

        return accountRepository.findByOwnerLogin(login).stream()
                .map(account -> new AccountDTO(account.getId(), account.getOwnerLogin(), account.getBalance()))
                .collect(Collectors.toList());
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountDTO(account.getId(), account.getOwnerLogin(), account.getBalance()))
                .collect(Collectors.toList());
    }
}
