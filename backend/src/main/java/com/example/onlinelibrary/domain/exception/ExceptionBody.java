package com.example.onlinelibrary.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Represents the body of an exception response.
 */
@Data
@AllArgsConstructor
public class ExceptionBody {

    /**
     * The main error message.
     */
    private String message;

    /**
     * Additional error details as a map of field names to error messages.
     */
    private Map<String, String> errors;

    /**
     * Constructs an ExceptionBody with a given main error message.
     *
     * @param message The main error message.
     */
    public ExceptionBody(String message) {
        this.message = message;
    }

}