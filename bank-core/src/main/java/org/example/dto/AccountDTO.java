package org.example.dto;

/**
 * Data Transfer Object for representing an account.
 */
public record AccountDTO(Long id, String ownerLogin, double balance) {
}
