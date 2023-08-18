package com.example.onlinelibrary.domain.exception;

/**
 * Custom exception indicating access denied for a specific operation.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs a new AccessDeniedException with no detail message.
     */
    public AccessDeniedException() {
        super();
    }

}