package org.example.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a transaction record in an account's history.
 */
@Getter
@RequiredArgsConstructor
public class TransactionHistory {
    private final String type;
    private final double amount;
    private final double newBalance;
}
