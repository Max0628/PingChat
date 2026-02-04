package com.pingchat.pingchat_backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

  @JsonProperty("id")
  private String id;

  @JsonProperty("provider")
  private String provider;

  @JsonProperty("email")
  private String email;

  @JsonProperty("name")
  private String name;

  @JsonProperty("avatarUrl")
  private String avatarUrl;

  @JsonProperty("createdAt")
  private String createdAt;
}
