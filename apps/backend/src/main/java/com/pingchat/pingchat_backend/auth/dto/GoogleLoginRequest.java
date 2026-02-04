package com.pingchat.pingchat_backend.auth.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request DTO for Google OAuth2 login.
 * Contains the access token received from Google OAuth2 flow.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoogleLoginRequest {

  /**
   * Access token obtained from Google OAuth2 authentication.
   * This token will be verified with Google's servers to retrieve user information.
   * Cannot be blank.
   */
  @JsonProperty("accessToken")
  @NotBlank(message = "Access token is required")
  private String accessToken;
}
