package com.mishateror.office.exceptions;

/**
 * Thrown when a Character attempts to cast an Ability without Action Points.
 */
public class NotEnoughApException extends Exception {
    /**
     * Instantiates a new Not enough ap exception.
     *
     * @param message the message
     */
    public NotEnoughApException(String message) {
        super(message);
    }
}
