package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Data Transfer Object for representing a transaction history record.
 */
@Getter
@RequiredArgsConstructor
public class TransactionHistoryDTO {
    private final String type;
    private final double amount;
    private final double newBalance;
}
