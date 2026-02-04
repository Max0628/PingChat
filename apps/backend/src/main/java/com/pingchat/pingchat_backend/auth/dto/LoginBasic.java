package com.pingchat.pingchat_backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginBasic {
  @JsonProperty("provider")
  private String provider;
}
