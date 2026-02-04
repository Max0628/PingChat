package com.pingchat.pingchat_backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for Google OAuth2 user information response.
 * Contains user profile data returned from Google's userinfo endpoint.
 * Reference: https://developers.google.com/identity/protocols/oauth2/openid-connect
 */
@Data
public class GoogleUserInfoDTO {

  /**
   * Unique identifier for the user's Google account.
   * This value is never reused even if the user changes their email.
   */
  @JsonProperty("sub")
  private String sub;

  /**
   * User's email address.
   * Only present if email scope was requested.
   */
  @JsonProperty("email")
  private String email;

  /**
   * Whether the email address has been verified.
   * Only present if email scope was requested.
   */
  @JsonProperty("email_verified")
  private Boolean emailVerified;

  /**
   * User's full name in displayable form.
   * Only present if profile scope was requested.
   */
  @JsonProperty("name")
  private String name;

  /**
   * User's given name (first name).
   * Only present if profile scope was requested.
   */
  @JsonProperty("given_name")
  private String givenName;

  /**
   * User's family name (last name).
   * Only present if profile scope was requested.
   */
  @JsonProperty("family_name")
  private String familyName;

  /**
   * URL of the user's profile picture.
   * Only present if profile scope was requested.
   */
  @JsonProperty("picture")
  private String picture;

  /**
   * User's preferred locale (BCP 47 language tag).
   * Only present if profile scope was requested.
   */
  @JsonProperty("locale")
  private String locale;
}

