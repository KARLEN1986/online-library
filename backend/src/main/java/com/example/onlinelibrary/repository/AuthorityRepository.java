package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.domain.enums.AuthorityName;
import com.example.onlinelibrary.domain.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing authority entities.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    /**
     * Find an authority by its name.
     *
     * @param name The name of the authority to retrieve.
     * @return The {@link Authority} entity representing the authority.
     */
    Authority findByName(AuthorityName name);

}