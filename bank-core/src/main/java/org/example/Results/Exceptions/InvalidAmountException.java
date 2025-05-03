package org.example.Results.Exceptions;

/**
 * Thrown when an invalid amount is provided for a transaction.
 */
public class InvalidAmountException extends RuntimeException {

    /**
     * Constructs the exception with the specified message.
     *
     * @param message the detail message
     */
    public InvalidAmountException(String message) {
        super(message);
    }
}
