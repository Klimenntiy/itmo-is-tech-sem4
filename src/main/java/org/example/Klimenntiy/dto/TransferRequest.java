package org.example.Klimenntiy.dto;

public record TransferRequest(Long senderId, Long receiverId, double amount) {
}
