package com.pingchat.pingchat_backend.auth.service;

public interface AuthService {


  Object generateJwtToken(Object object);

  Object googleOauth2Verify(Object object);

}