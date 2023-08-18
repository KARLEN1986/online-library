package com.example.onlinelibrary.domain.exception;

/**
 * Custom exception indicating a failure in mapping resources.
 */
public class ResourceMappingException extends RuntimeException {

    /**
     * Constructs a new ResourceMappingException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public ResourceMappingException(String message) {
        super(message);
    }

}