package com.pingchat.pingchat_backend.auth.repository;

import com.pingchat.pingchat_backend.auth.model.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for UserAuthProvider entity.
 * Handles OAuth2 provider authentication data persistence.
 */
@Repository
public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {

  /**
   * Find authentication provider by provider name and provider ID.
   * @param provider the OAuth2 provider name (e.g., "google")
   * @param providerId the unique ID from the provider
   * @return Optional containing UserAuthProvider if found
   */
  Optional<UserAuthProvider> findByProviderAndProviderId(String provider, String providerId);

  /**
   * Check if authentication provider exists by provider name and provider ID.
   * @param provider the OAuth2 provider name
   * @param providerId the unique ID from the provider
   * @return true if exists, false otherwise
   */
  boolean existsByProviderAndProviderId(String provider, String providerId);

  /**
   * Find all authentication providers for a specific user.
   * @param userId the user ID
   * @return Optional containing UserAuthProvider if found
   */
  Optional<UserAuthProvider> findByUserId(UUID userId);
}

