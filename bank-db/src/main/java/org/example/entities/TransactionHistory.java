package org.example.entities;


/**
 * Represents a transaction record in an account's history.
 */
public record TransactionHistory(String type, double amount, double newBalance) {
}
