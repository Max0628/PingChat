package com.pingchat.pingchat_backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pingchat.pingchat_backend.auth.model.AppUser;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for AppUser entity.
 * Provides standard CRUD and custom query methods.
 */
public interface UserRepository extends JpaRepository<AppUser, UUID> {

    /**
     * Check if a user exists by email.
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find a user by email.
     * @param email the email to search for
     * @return Optional containing AppUser if found
     */
    Optional<AppUser> findByEmail(String email);
}
