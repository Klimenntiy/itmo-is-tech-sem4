package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a transaction record in the system.
 */
@Entity
@Table(name = "transaction_history")
@Getter
@NoArgsConstructor
@ToString
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double newBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * Constructs a new transaction record.
     *
     * @param type       The type of transaction (e.g., deposit, withdrawal, transfer).
     * @param amount     The transaction amount.
     * @param newBalance The account balance after the transaction.
     * @param account    The account associated with this transaction.
     */
    public TransactionHistory(String type, double amount, double newBalance, Account account) {
        this.type = type;
        this.amount = amount;
        this.newBalance = newBalance;
        this.account = account;
    }
}
