package com.pingchat.pingchat_backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NativeSignUpRequest {

  @NotBlank
  @Email
  @JsonProperty("email")
  private String email;

  @NotBlank
  @Size(min = 8, max = 32)
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=]{8,32}$",
          message = "密碼需包含字母和數字，長度8-32")
  @JsonProperty("password")
  private String password;

  @NotBlank
  @Size(min = 2, max = 32)
  @JsonProperty("name")
  private String name;
}