package com.example.onlinelibrary.service;


import com.example.onlinelibrary.domain.book.Book;

import java.util.List;

/**
 * Service interface for managing book-related operations.
 */
public interface BookService {

    /**
     * Get a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The {@link Book} entity representing the book.
     */
    Book getBookById(Long id);

    /**
     * Update book information.
     *
     * @param book The {@link Book} entity with updated information.
     * @return The updated {@link Book} entity.
     */
    Book update(Book book);

    /**
     * Create a new book and associate it with a specific user.
     *
     * @param book   The {@link Book} entity representing the new book.
     * @param userId The ID of the user to whom the book will be associated.
     * @return The created {@link Book} entity.
     */
    Book create(Book book, Long userId);

    /**
     * Delete a book by its ID.
     *
     * @param id The ID of the book to delete.
     */
    void delete(Long id);

    /**
     * Get a list of books by a specific genre.
     *
     * @param genre The genre of the books to retrieve.
     * @return A list of {@link Book} entities with the specified genre.
     */
    List<Book> getBooksByGenre(String genre);

    /**
     * Get a list of all books.
     *
     * @return A list of all {@link Book} entities.
     */
    List<Book> getAllBooks();

    /**
     * Get a list of books associated with a specific user.
     *
     * @param id The ID of the user whose books will be retrieved.
     * @return A list of {@link Book} entities associated with the user.
     */
    List<Book> getAllByUserId(Long id);

}
