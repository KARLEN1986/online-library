package com.example.onlinelibrary.service.impl;

import com.example.onlinelibrary.domain.exception.ResourceNotFoundException;
import com.example.onlinelibrary.domain.user.Authority;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.repository.AuthorityRepository;
import com.example.onlinelibrary.repository.UserRepository;
import com.example.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.onlinelibrary.domain.enums.AuthorityName.ROLE_USER;

/**
 * Implementation of the UserService interface for managing user-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        logger.debug("Getting user by ID: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User with ID {} not found", id);
                    return new ResourceNotFoundException("User not found.");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        logger.debug("Getting user by username: {}", username);

        return userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    logger.error("User with username {} not found", username);
                    return new ResourceNotFoundException("User not found.");
                });
    }

    @Override
    @Transactional
    public User update(User user) {
        logger.debug("Updating user with ID {}", user.getId());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User updatedUser = userRepository.save(user);

        logger.info("Updated user with ID {}", updatedUser.getId());

        return updatedUser;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Authority roleUser = authorityRepository.findByName(ROLE_USER);

        Set<Authority> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setAuthorities(userRoles);
        userRepository.save(user);

        logger.info("Created a new user with email: {}", user.getEmail());

        return user;
    }


    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
        logger.info("Deleted user with ID: {}", id);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isBookAssignToUser(Long userId, Long bookId) {
        logger.debug("Checking if book with ID {} is assigned to user with ID {}", bookId, userId);

        Optional<User> userByIdAndBooksId = userRepository.findByIdAndBooks_Id(userId, bookId);
        boolean isAssigned = userByIdAndBooksId.isPresent();

        if (isAssigned) {
            logger.debug("Book with ID {} is assigned to user with ID {}", bookId, userId);
        } else {
            logger.debug("Book with ID {} is not assigned to user with ID {}", bookId, userId);
        }

        return isAssigned;
    }

}
