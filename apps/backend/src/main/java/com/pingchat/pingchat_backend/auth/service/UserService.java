package com.pingchat.pingchat_backend.auth.service;

import com.pingchat.pingchat_backend.auth.dto.AuthResponse;
import com.pingchat.pingchat_backend.auth.dto.NativeSignUpRequest;
import com.pingchat.pingchat_backend.auth.dto.NativeLoginRequest;
import com.pingchat.pingchat_backend.auth.dto.GoogleLoginRequest;

/**
 * Service interface for user authentication operations.
 */
public interface UserService {

  /**
   * Register a new user with native credentials.
   * @param request the signup request containing user information
   * @return AuthResponse with JWT token and user information
   */
  AuthResponse signup(NativeSignUpRequest request);

  /**
   * Authenticate user with native (email/password) credentials.
   * @param request the login request containing email and password
   * @return AuthResponse with JWT token and user information
   */
  AuthResponse loginNative(NativeLoginRequest request);

  /**
   * Authenticate user with Google OAuth2 credentials.
   * @param request the login request containing Google access token
   * @return AuthResponse with JWT token and user information
   */
  AuthResponse loginGoogle(GoogleLoginRequest request);

}
