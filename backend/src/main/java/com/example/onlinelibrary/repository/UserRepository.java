package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.domain.enums.AuthorityName;
import com.example.onlinelibrary.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing user entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return An {@link Optional} containing the {@link User} entity representing the user.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by their ID and a specific book ID that they are associated with.
     *
     * @param userId  The ID of the user.
     * @param bookId  The ID of the book associated with the user.
     * @return An {@link Optional} containing the {@link User} entity representing the user.
     */
    Optional<User> findByIdAndBooks_Id(Long userId, Long bookId);

    /**
     * Find users by the name of a specific authority role.
     *
     * @param roleName The name of the authority role.
     * @return A list of {@link User} entities with the specified authority role.
     */
    List<User> findByAuthorities_Name(AuthorityName roleName);
}
