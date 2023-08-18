package com.example.onlinelibrary.domain.exception;

/**
 * Custom exception indicating that a requested resource was not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}