package org.example.Results.Exceptions;

/**
 * Thrown when there are insufficient funds for a transaction.
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Constructs the exception with the specified message.
     *
     * @param message the detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
