package com.pingchat.pingchat_backend.auth.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing OAuth2 provider authentication information for users.
 * Maps to the user_auth_provider table.
 * Stores third-party authentication provider details (Google, Facebook, etc.).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_auth_provider", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"provider", "provider_id"})
})
public class UserAuthProvider {

  /**
   * Auto-generated primary key.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * Reference to the user ID in app_user table.
   */
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  /**
   * OAuth2 provider name (e.g., "google", "facebook", "apple").
   */
  @Column(name = "provider", nullable = false, length = 20)
  private String provider;

  /**
   * Unique identifier from the OAuth2 provider (e.g., Google sub).
   */
  @Column(name = "provider_id", nullable = false, length = 128)
  private String providerId;

  /**
   * Timestamp when this provider authentication was created.
   */
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  /**
   * Set created_at timestamp before persisting.
   */
  @PrePersist
  protected void onCreate() {
    if (createdAt == null) {
      createdAt = Instant.now();
    }
  }
}