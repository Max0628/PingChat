package com.pingchat.pingchat_backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Response DTO for authentication operations (login/signup).
 * Contains JWT access token and user information.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

  /**
   * JWT access token for authenticated requests.
   */
  @JsonProperty("accessToken")
  private String accessToken;

  /**
   * Token expiration time in seconds.
   */
  @JsonProperty("expiresIn")
  private Long expiresIn;

  /**
   * Authenticated user information.
   */
  @JsonProperty("user")
  private UserDTO user;
}
