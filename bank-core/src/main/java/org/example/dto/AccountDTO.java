package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Data Transfer Object for representing an account.
 */
@Getter
@RequiredArgsConstructor
public class AccountDTO {
    private final String id;
    private final String ownerLogin;
    private final double balance;
}
