package org.example.Results.Exceptions;

/**
 * Thrown when an account is not found.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Constructs the exception with the specified message.
     *
     * @param message the detail message
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
