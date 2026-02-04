package com.pingchat.pingchat_backend.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.pingchat.pingchat_backend.auth.model.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT utility class for generating and validating JWT tokens.
 */
@Slf4j
@Component
public class JwtUtil {

  @Value("${jwt.secret:pingchat-default-secret-key-change-this-in-production-min-256-bits}")
  private String secret;

  @Value("${jwt.expiration:86400000}")
  private Long expiration;


  /**
   * Generate JWT token for a user.
   * @param user
   * @return JWT token string
   */
  public String generateToken(AppUser user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getId());
    claims.put("email", user.getEmail());
    claims.put("username", user.getUsername());
    return createToken(claims, user.getEmail());
  }

  /**
   * Create JWT token with claims and subject.
   * @param claims  Custom claims
   * @param subject Subject (usually email)
   * @return JWT token string
   */
  private String createToken(Map<String, Object> claims, String subject) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);

    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact();
  }

  /**
   * Extract username (email) from token.
   * @param token JWT token
   * @return Username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extract user ID from token.
   * @param token JWT token
   * @return User ID as UUID
   */
  public UUID extractUserId(String token) {
    Claims claims = extractAllClaims(token);
    String userIdStr = claims.get("userId", String.class);
    return UUID.fromString(userIdStr);
  }

  /**
   * Extract expiration date from token.
   * @param token JWT token
   * @return Expiration date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extract a specific claim from token.
   * @param token          JWT token
   * @param claimsResolver Function to extract claim
   * @param <T>            Claim type
   * @return Claim value
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extract all claims from token.
   * @param token JWT token
   * @return All claims
   */
  private Claims extractAllClaims(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  /**
   * Check if token is expired.
   * @param token JWT token
   * @return true if expired, false otherwise
   */
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Validate JWT token.
   * @param token    JWT token
   * @param username Username to validate against
   * @return true if valid, false otherwise
   */
  public Boolean validateToken(String token, String username) {
    try {
      final String extractedUsername = extractUsername(token);
      return (extractedUsername.equals(username) && !isTokenExpired(token));
    } catch (Exception e) {
      log.error("Token validation failed: {}", e.getMessage());
      return false;
    }
  }

  /**
   * Validate JWT token without username check.
   * @param token JWT token
   * @return true if valid, false otherwise
   */
  public Boolean validateToken(String token) {
    try {
      extractAllClaims(token);
      return !isTokenExpired(token);
    } catch (Exception e) {
      log.error("Token validation failed: {}", e.getMessage());
      return false;
    }
  }

  /**
   * Get expiration time in milliseconds.
   * @return Expiration time
   */
  public Long getExpiration() {
    return expiration;
  }
}

