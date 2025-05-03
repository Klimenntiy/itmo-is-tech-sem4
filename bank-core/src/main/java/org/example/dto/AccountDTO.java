package org.example.dto;

/**
 * Data Transfer Object for representing an account.
 */

public record AccountDTO(String id, String ownerLogin, double balance) {
}
