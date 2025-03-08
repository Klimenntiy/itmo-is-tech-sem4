package org.example.Results;

import lombok.Getter;

/**
 * Represents a successful result with a message.
 */
@Getter
public class SuccessResult {
    private final String message;

    /**
     * Constructs a SuccessResult with the specified message.
     *
     * @param message the success message
     */
    public SuccessResult(String message) {
        this.message = message;
    }
}
