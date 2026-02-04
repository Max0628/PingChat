package com.pingchat.pingchat_backend.auth.service;

import com.pingchat.pingchat_backend.auth.dto.GoogleUserInfoDTO;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Service for interacting with Google OAuth2 APIs.
 * Handles token verification and user information retrieval.
 */
@Service
public class GoogleOAuth2Service {

  private static final String GOOGLE_USERINFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

  private final RestTemplate restTemplate;

  public GoogleOAuth2Service() {
    this.restTemplate = new RestTemplate();
  }

  /**
   * Verify Google access token and retrieve user information.
   * Makes a request to Google's userinfo endpoint with the provided access token.
   *
   * @param accessToken the access token received from Google OAuth2 flow
   * @return GoogleUserInfoDTO containing user information
   * @throws IllegalArgumentException if token is invalid or expired
   */
  public GoogleUserInfoDTO verifyTokenAndGetUserInfo(String accessToken) {
    try {
      // Create headers with Bearer token
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);
      headers.setContentType(MediaType.APPLICATION_JSON);

      // Create HTTP entity with headers
      HttpEntity<String> entity = new HttpEntity<>(headers);

      // Make GET request to Google's userinfo endpoint
      ResponseEntity<GoogleUserInfoDTO> response = restTemplate.exchange(
          GOOGLE_USERINFO_ENDPOINT,
          HttpMethod.GET,
          entity,
          GoogleUserInfoDTO.class
      );

      // Check if response is successful
      if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
        GoogleUserInfoDTO userInfo = response.getBody();

        // Validate essential fields
        if (userInfo.getSub() == null || userInfo.getEmail() == null) {
          throw new IllegalArgumentException("Invalid user info received from Google: missing sub or email");
        }

        return userInfo;
      } else {
        throw new IllegalArgumentException("Failed to retrieve user info from Google");
      }

    } catch (HttpClientErrorException.Unauthorized e) {
      throw new IllegalArgumentException("Invalid or expired Google access token", e);
    } catch (HttpClientErrorException e) {
      throw new IllegalArgumentException("Failed to verify Google token: " + e.getMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Error communicating with Google OAuth2 service: " + e.getMessage(), e);
    }
  }
}

