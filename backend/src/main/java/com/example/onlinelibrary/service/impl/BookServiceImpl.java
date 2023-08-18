package com.example.onlinelibrary.service.impl;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.exception.ResourceNotFoundException;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the BookService interface for managing book-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        logger.debug("Getting book by ID: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        logger.debug("Getting all books");
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllByUserId(Long userId) {
        logger.debug("Getting all books for user with ID: {}", userId);
        return bookRepository.findByUsers_Id(userId);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        logger.debug("Updating book with ID: {}", book.getId());
        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book create(Book book, Long userId) {
        logger.debug("Creating book with ID: {} and associating with user ID: {}", book.getId(), userId);
        bookRepository.save(book);
        bookRepository.assignBookToUser(userId, book.getId());
        return book;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.debug("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        logger.debug("Getting books by genre: {}", genre);
        return bookRepository.findByGenre(genre);
    }

}
