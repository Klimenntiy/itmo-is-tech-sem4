package org.example.dto;

/**
 * Data Transfer Object for representing a transaction history record.
 */

public record TransactionHistoryDTO(String type, double amount, double newBalance) {
}
