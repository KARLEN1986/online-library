package com.example.onlinelibrary.service;

import com.example.onlinelibrary.domain.user.User;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Get a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} entity representing the user.
     */
    User getById(Long id);

    /**
     * Get a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The {@link User} entity representing the user.
     */
    User getByUsername(String username);

    /**
     * Update user information.
     *
     * @param user The {@link User} entity with updated information.
     * @return The updated {@link User} entity.
     */
    User update(User user);

    /**
     * Create a new user.
     *
     * @param user The {@link User} entity representing the new user.
     * @return The created {@link User} entity.
     */
    User create(User user);

    /**
     * Delete a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    void delete(Long id);

    /**
     * Check if a specific book is assigned to a user.
     *
     * @param userId The ID of the user to check.
     * @param bookId The ID of the book to check.
     * @return True if the book is assigned to the user, false otherwise.
     */
    boolean isBookAssignToUser(Long userId, Long bookId);

}
