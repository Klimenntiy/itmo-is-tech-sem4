package org.example;

import org.example.dto.AccountDTO;
import org.example.dto.TransactionHistoryDTO;
import org.example.dto.UserDTO;
import org.example.services.UserService;
import org.example.services.AccountService;
import org.example.services.TransactionService;
import org.example.entities.enums.Gender;
import org.example.entities.enums.HairColor;

import java.util.List;
import java.util.Scanner;

/**
 * Provides a console menu for interacting with the banking system.
 */
public class Menu {
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final Scanner scanner;

    /**
     * Constructs a menu with required services and a scanner for user input.
     *
     * @param userService         Service for user management.
     * @param accountService      Service for account management.
     * @param transactionService  Service for handling transactions.
     * @param scanner             Scanner for user input.
     */
    public Menu(UserService userService, AccountService accountService, TransactionService transactionService, Scanner scanner) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.scanner = scanner;
    }

    /**
     * Runs the console menu, allowing users to interact with the banking system.
     */
    public void run() {
        while (true) {
            System.out.println("\n=== BANKING SYSTEM ===");
            System.out.println("1. Create a user");
            System.out.println("2. Add a friend");
            System.out.println("3. Remove a friend");
            System.out.println("4. Create an account");
            System.out.println("5. Deposit money");
            System.out.println("6. Withdraw money");
            System.out.println("7. Transfer money");
            System.out.println("8. Show account balance");
            System.out.println("9. Show all users");
            System.out.println("10. Show user accounts");
            System.out.println("11. Show transaction history");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> createUser();
                case "2" -> addFriend();
                case "3" -> removeFriend();
                case "4" -> createAccount();
                case "5" -> depositMoney();
                case "6" -> withdrawMoney();
                case "7" -> transferMoney();
                case "8" -> showBalance();
                case "9" -> showAllUsers();
                case "10" -> showUserAccounts();
                case "11" -> showTransactionHistory();
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid input, please try again.");
            }
        }
    }

    private void createUser() {
        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter gender (MALE/FEMALE): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter hair color (BLACK, BROWN, BLONDE, RED, OTHER): ");
        HairColor hairColor = HairColor.valueOf(scanner.nextLine().toUpperCase());
        userService.createUser(login, name, age, gender, hairColor);
    }

    private void addFriend() {
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter friend's login: ");
        String friendLogin = scanner.nextLine();
        userService.addFriend(login, friendLogin);
    }

    private void removeFriend() {
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter friend's login: ");
        String friendLogin = scanner.nextLine();
        userService.removeFriend(login, friendLogin);
    }

    private void createAccount() {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        accountService.createAccount(login);
    }

    private void depositMoney() {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        accountService.depositMoney(accountId, amount);
    }

    private void withdrawMoney() {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        accountService.withdrawMoney(accountId, amount);
    }

    private void transferMoney() {
        System.out.print("Enter sender's account ID: ");
        String fromAccount = scanner.nextLine();
        System.out.print("Enter recipient's account ID: ");
        String toAccount = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        transactionService.transferMoney(fromAccount, toAccount, amount);
    }

    private void showBalance() {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        accountService.showBalance(accountId);
    }

    private void showAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("\n=== User List ===");
            for (UserDTO user : users) {
                System.out.println("Login: " + user.login());
                System.out.println("Name: " + user.name());
                System.out.println("Age: " + user.age());
                System.out.println("Gender: " + user.gender());
                System.out.println("Hair Color: " + user.hairColor());
                System.out.println("Friends: " + (user.friends().isEmpty() ? "No friends" : user.friends()));
                System.out.println("---------------------------");
            }
        }
    }

    private void showUserAccounts() {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        List<AccountDTO> accounts = accountService.getUserAccounts(login);

        if (accounts.isEmpty()) {
            System.out.println("User has no accounts.");
        } else {
            System.out.println("Accounts for user " + login + ":");
            for (AccountDTO account : accounts) {
                System.out.println("ID: " + account.id() +
                        ", Balance: " + account.balance());
            }
        }
    }

    private void showTransactionHistory() {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        List<TransactionHistoryDTO> transactions = transactionService.getTransactionHistory(accountId);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction history for account " + accountId + ":");
            for (TransactionHistoryDTO tx : transactions) {
                System.out.println("Type: " + tx.type() +
                        ", Amount: " + tx.amount() +
                        ", Balance after transaction: " + tx.newBalance());
            }
        }
    }
}
