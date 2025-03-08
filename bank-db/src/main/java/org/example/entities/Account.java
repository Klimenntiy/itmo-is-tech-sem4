package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account.
 */
@Getter
@ToString
public class Account {
    @Getter
    private final String id;
    private final String ownerLogin;
    @Getter
    @Setter
    private double balance;
    private final List<TransactionHistory> transactions = new ArrayList<>();

    /**
     * Creates a new account with a unique identifier.
     *
     * @param ownerLogin The login of the account owner
     */
    public Account(String ownerLogin) {
        this.id = generateId();
        this.ownerLogin = ownerLogin;
        this.balance = 0.0;
    }

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount The deposit amount
     */
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new TransactionHistory("Deposit", amount, balance));
    }

    /**
     * Withdraws a specified amount from the account if there are sufficient funds.
     *
     * @param amount The withdrawal amount
     * @return true if the transaction is successful, otherwise false
     */
    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactions.add(new TransactionHistory("Withdrawal", amount, balance));
        return true;
    }

    /**
     * Transfers money to another account, including a commission fee.
     *
     * @param receiver   The recipient account
     * @param amount     The transfer amount
     * @param commission The commission fee
     * @return true if the transfer is successful, otherwise false
     */
    public boolean transferTo(Account receiver, double amount, double commission) {
        double totalAmount = amount + commission;
        if (withdraw(totalAmount)) {
            receiver.deposit(amount);
            transactions.add(new TransactionHistory("Transfer to " + receiver.getId(), totalAmount, balance));
            receiver.transactions.add(new TransactionHistory("Transfer from " + this.id, amount, receiver.getBalance()));
            return true;
        }
        return false;
    }

    /**
     * Returns the account's transaction history.
     *
     * @return A list of transactions
     */
    public List<TransactionHistory> getTransactionHistory() {
        return transactions;
    }

    /**
     * Generates a unique account identifier.
     *
     * @return The unique account ID
     */
    private String generateId() {
        return "ACC" + System.currentTimeMillis();
    }

}
