package com.pingchat.pingchat_backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for native (email/password) login.
 * Contains user credentials for authentication.
 */
@Data
@NoArgsConstructor
public class NativeLoginRequest {

  /**
   * User's email address.
   * Must be a valid email format and cannot be blank.
   */
  @JsonProperty("email")
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  private String email;

  /**
   * User's password.
   * Cannot be blank.
   */
  @JsonProperty("password")
  @NotBlank(message = "Password is required")
  private String password;
}
