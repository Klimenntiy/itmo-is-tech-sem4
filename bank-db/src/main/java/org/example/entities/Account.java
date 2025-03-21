package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account entity.
 */
@Entity
@Table(name = "accounts")
@Getter
@ToString
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ownerLogin;

    private double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionHistory> transactions = new ArrayList<>();

    /**
     * Constructs a new account with the given owner login.
     *
     * @param ownerLogin The login of the account owner.
     */
    public Account(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        this.balance = 0.0;
    }

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new TransactionHistory("Deposit", amount, balance, this));
    }

    /**
     * Withdraws a specified amount from the account if sufficient funds exist.
     *
     * @param amount The amount to withdraw.
     * @return True if withdrawal is successful, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactions.add(new TransactionHistory("Withdrawal", amount, balance, this));
        return true;
    }

    /**
     * Transfers money to another account, applying a commission.
     *
     * @param receiver  The receiving account.
     * @param amount    The amount to transfer.
     * @param commission The commission fee.
     * @return True if the transfer is successful, false otherwise.
     */
    public boolean transferTo(Account receiver, double amount, double commission) {
        double totalAmount = amount + commission;
        if (withdraw(totalAmount)) {
            receiver.deposit(amount);
            transactions.add(new TransactionHistory("Transfer to " + receiver.getId(), totalAmount, balance, this));
            receiver.getTransactions().add(new TransactionHistory("Transfer from " + this.id, amount, receiver.getBalance(), receiver));
            return true;
        }
        return false;
    }
}
