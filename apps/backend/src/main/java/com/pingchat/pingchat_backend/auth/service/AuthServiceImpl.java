package com.pingchat.pingchat_backend.auth.service;

import com.pingchat.pingchat_backend.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository authRepository;

  public AuthServiceImpl(UserRepository authRepository) {
    this.authRepository = authRepository;
  }

  @Override
  public Object generateJwtToken(Object object) {
    // TODO: implement JWT token generation
    throw new UnsupportedOperationException("JWT token generation not implemented yet");
  }

  @Override
  public Object googleOauth2Verify(Object request) {
    // TODO: implement Google OAuth2 verification
    return null;
  }

}
