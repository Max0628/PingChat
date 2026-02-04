package com.pingchat.pingchat_backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.pingchat.pingchat_backend.auth.model.UserPassword;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for UserPassword entity.
 * Provides standard CRUD and custom query methods for user_password table.
 */
public interface UserPasswordRepository extends JpaRepository<UserPassword, UUID> {

  /**
   * Find password hash by user ID.
   * @param userId the user ID
   * @return Optional containing UserPassword if found
   */
  Optional<UserPassword> findByUserId(UUID userId);

  /**
   * Find password hash by user email using a join query.
   * @param email the user email
   * @return Optional containing password hash if found
   */
  @Query("SELECT up.passwordHash FROM UserPassword up JOIN AppUser u ON up.userId = u.id WHERE u.email = :email")
  Optional<String> findPasswordHashByEmail(@Param("email") String email);
}

