package org.example.Klimenntiy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    public Account(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new TransactionHistory("Deposit", amount, balance, this));
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactions.add(new TransactionHistory("Withdrawal", amount, balance, this));
        return true;
    }

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
