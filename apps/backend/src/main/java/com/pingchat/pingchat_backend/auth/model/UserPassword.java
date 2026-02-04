package com.pingchat.pingchat_backend.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "user_password")
public class UserPassword {
  @Id
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;
}

