package com.pingchat.pingchat_backend.auth.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pingchat.pingchat_backend.auth.dto.AuthResponse;
import com.pingchat.pingchat_backend.auth.dto.GoogleLoginRequest;
import com.pingchat.pingchat_backend.auth.dto.GoogleUserInfoDTO;
import com.pingchat.pingchat_backend.auth.dto.NativeLoginRequest;
import com.pingchat.pingchat_backend.auth.dto.NativeSignUpRequest;
import com.pingchat.pingchat_backend.auth.dto.UserDTO;
import com.pingchat.pingchat_backend.auth.model.AppUser;
import com.pingchat.pingchat_backend.auth.model.UserAuthProvider;
import com.pingchat.pingchat_backend.auth.model.UserPassword;
import com.pingchat.pingchat_backend.auth.repository.UserAuthProviderRepository;
import com.pingchat.pingchat_backend.auth.repository.UserPasswordRepository;
import com.pingchat.pingchat_backend.auth.repository.UserRepository;
import com.pingchat.pingchat_backend.common.util.JwtUtil;
import com.pingchat.pingchat_backend.common.util.UuidGeneratorService;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserPasswordRepository userPasswordRepository;
  private final UuidGeneratorService uuidGeneratorService;
  private final JwtUtil jwtUtil;
  private final GoogleOAuth2Service googleOAuth2Service;
  private final UserAuthProviderRepository userAuthProviderRepository;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserPasswordRepository userPasswordRepository, UuidGeneratorService uuidGeneratorService, JwtUtil jwtUtil, GoogleOAuth2Service googleOAuth2Service, UserAuthProviderRepository userAuthProviderRepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userPasswordRepository = userPasswordRepository;
    this.uuidGeneratorService = uuidGeneratorService;
    this.jwtUtil = jwtUtil;
    this.googleOAuth2Service = googleOAuth2Service;
    this.userAuthProviderRepository = userAuthProviderRepository;
  }

  @Override
  public AuthResponse signup(NativeSignUpRequest request) {
    // 1. Check if email already exists
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already exists: " + request.getEmail());
    }

    // 2. Generate UUID for new user
    UUID uuid = uuidGeneratorService.generateUuidV7();

    // 3. Build and save AppUser
    AppUser appUser = AppUser.builder()
        .id(uuid)
        .email(request.getEmail())
        .username(request.getName())
        .build();

    // 4. Hash password and build UserPassword
    String hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
    UserPassword userPassword = UserPassword.builder()
        .userId(uuid)
        .passwordHash(hashedPassword)
        .build();

    // 5. Save to database
    AppUser savedUser = userRepository.save(appUser);
    userPasswordRepository.save(userPassword);

    // 6. Generate JWT token
    String jwtToken = jwtUtil.generateToken(savedUser);

    // 7. Build UserDTO
    UserDTO userDTO = UserDTO.builder()
        .id(savedUser.getId().toString())
        .email(savedUser.getEmail())
        .name(savedUser.getUsername())
        .build();

    // 8. Build and return AuthResponse
    return AuthResponse.builder()
        .accessToken(jwtToken)
        .expiresIn(jwtUtil.getExpiration())
        .user(userDTO)
        .build();
  }

  @Override
  public AuthResponse loginNative(NativeLoginRequest request) {
    // 1. Check if user exists
    AppUser appUser = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + request.getEmail()));

    // 2. Verify password
    String hashedPasswordFromDb = userPasswordRepository.findPasswordHashByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Password not found for user"));

    // Use BCrypt's matches method to compare raw password with hashed password
    if (!bCryptPasswordEncoder.matches(request.getPassword(), hashedPasswordFromDb)) {
      throw new IllegalArgumentException("Invalid password");
    }

    // 3. Generate JWT token
    String jwtToken = jwtUtil.generateToken(appUser);

    // 4. Build UserDTO
    UserDTO userDTO = UserDTO.builder()
        .id(appUser.getId().toString())
        .email(appUser.getEmail())
        .name(appUser.getUsername())
        .build();

    // 5. Build and return AuthResponse
    return AuthResponse.builder()
        .accessToken(jwtToken)
        .expiresIn(jwtUtil.getExpiration())
        .user(userDTO)
        .build();
  }

  @Override
  public AuthResponse loginGoogle(GoogleLoginRequest request) {
    // 1. Verify Google access token and get user info
    GoogleUserInfoDTO googleUserInfo = googleOAuth2Service.verifyTokenAndGetUserInfo(request.getAccessToken());

    // 2. Check if this Google account is already linked to a user
    Optional<UserAuthProvider> existingProvider = userAuthProviderRepository
        .findByProviderAndProviderId("google", googleUserInfo.getSub());

    AppUser appUser;

    if (existingProvider.isPresent()) {
      // 3a. Existing user - retrieve from database
      UUID userId = existingProvider.get().getUserId();
      appUser = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("User not found for provider"));

    } else {
      // 3b. New user - register account

      // Check if email is already used by another authentication method
      Optional<AppUser> existingUserByEmail = userRepository.findByEmail(googleUserInfo.getEmail());

      if (existingUserByEmail.isPresent()) {
        // Email exists but not linked to Google - link the accounts
        appUser = existingUserByEmail.get();

        // Create provider link
        UserAuthProvider newProvider = UserAuthProvider.builder()
            .userId(appUser.getId())
            .provider("google")
            .providerId(googleUserInfo.getSub())
            .build();
        userAuthProviderRepository.save(newProvider);

      } else {
        // Brand new user - create account
        UUID newUserId = uuidGeneratorService.generateUuidV7();

        // Create AppUser
        appUser = AppUser.builder()
            .id(newUserId)
            .email(googleUserInfo.getEmail())
            .username(googleUserInfo.getName() != null ? googleUserInfo.getName() : googleUserInfo.getEmail().split("@")[0])
            .avatarUrl(googleUserInfo.getPicture())
            .build();

        // Save user
        appUser = userRepository.save(appUser);

        // Create provider link
        UserAuthProvider newProvider = UserAuthProvider.builder()
            .userId(newUserId)
            .provider("google")
            .providerId(googleUserInfo.getSub())
            .build();
        userAuthProviderRepository.save(newProvider);
      }
    }

    // 4. Generate JWT token
    String jwtToken = jwtUtil.generateToken(appUser);

    // 5. Build UserDTO
    UserDTO userDTO = UserDTO.builder()
        .id(appUser.getId().toString())
        .email(appUser.getEmail())
        .name(appUser.getUsername())
        .build();

    // 6. Build and return AuthResponse
    return AuthResponse.builder()
        .accessToken(jwtToken)
        .expiresIn(jwtUtil.getExpiration())
        .user(userDTO)
        .build();
  }



}
