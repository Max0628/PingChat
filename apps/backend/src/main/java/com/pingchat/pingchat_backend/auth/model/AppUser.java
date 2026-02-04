package com.pingchat.pingchat_backend.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;
@Data
@Builder
@Entity
@Table(name = "app_user")
public class AppUser {
  @Id
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "username", length = 50)
  private String username;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "introduction")
  private String introduction;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;
}
