package org.example.api_gateway.models.dto;

public record TransactionHistoryDTO(String type, double amount, double newBalance) {
}
