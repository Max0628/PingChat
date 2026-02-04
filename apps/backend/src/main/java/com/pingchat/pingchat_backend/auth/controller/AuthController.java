package com.pingchat.pingchat_backend.auth.controller;


import com.pingchat.pingchat_backend.auth.service.AuthService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  /**
   * handle for login request
   *
   * @return
   */
  @PatchMapping
  public Object login() {
    //todo native login
    //todo google oauth2 login
    return null;

  }

  /**
   * handle register request
   */
  public Object register() {
    //todo native login
    //todo google oauth2 login

    return null;
  }

  /**
   * accept access_token and verify it with google oauth2 server
   */
  public Object googleOauth2Verify() {
    //todo
    return null;
  }


}