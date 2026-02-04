package com.pingchat.pingchat_backend.auth.controller;

import com.pingchat.pingchat_backend.auth.dto.AuthResponse;
import com.pingchat.pingchat_backend.auth.dto.NativeSignUpRequest;
import com.pingchat.pingchat_backend.auth.dto.NativeLoginRequest;
import com.pingchat.pingchat_backend.auth.dto.GoogleLoginRequest;
import com.pingchat.pingchat_backend.auth.service.UserService;
import com.pingchat.pingchat_backend.common.dto.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Handle native user registration.
   * @param request NativeSignUpRequest containing email, name, and password
   * @return AuthResponse with JWT token and user information, or ErrorResponse on failure
   */
  @PostMapping(":register")
  public ResponseEntity<?> signUp(@Valid @RequestBody NativeSignUpRequest request) {
    try {
      AuthResponse response = userService.signup(request);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_REQUEST", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new ErrorResponse("INTERNAL_ERROR", "系統錯誤，請稍後再試"));
    }
  }

  /**
   * Handle native login with email and password.
   * @param request NativeLoginRequest containing email and password
   * @return AuthResponse with JWT token and user information, or ErrorResponse on failure
   */
  @PostMapping(":login/native")
  public ResponseEntity<?> loginNative(@Valid @RequestBody NativeLoginRequest request) {
    try {
      AuthResponse response = userService.loginNative(request);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_REQUEST", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new ErrorResponse("INTERNAL_ERROR", "系統錯誤，請稍後再試"));
    }
  }

  /**
   * Handle Google OAuth2 login.
   * @param request GoogleLoginRequest containing Google access token
   * @return AuthResponse with JWT token and user information, or ErrorResponse on failure
   */
  @PostMapping(":login/google")
  public ResponseEntity<?> loginGoogle(@Valid @RequestBody GoogleLoginRequest request) {
    try {
      AuthResponse response = userService.loginGoogle(request);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_REQUEST", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new ErrorResponse("INTERNAL_ERROR", "系統錯誤，請稍後再試"));
    }
  }


}
