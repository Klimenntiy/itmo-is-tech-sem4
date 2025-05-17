package org.example;

import org.example.repositories.AccountRepository;
import org.example.repositories.UserRepository;
import org.example.services.AccountService;
import org.example.services.TransactionService;
import org.example.services.UserService;

import java.util.Scanner;

/**
 * Entry point for the banking console application.
 */
public class BankConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();
        AccountRepository accountRepository = new AccountRepository();

        UserService userService = new UserService(userRepository);
        AccountService accountService = new AccountService(accountRepository, userRepository);
        TransactionService transactionService = new TransactionService(accountRepository, userRepository);

        Menu menu = new Menu(userService, accountService, transactionService, scanner);
        menu.run();
    }
}
