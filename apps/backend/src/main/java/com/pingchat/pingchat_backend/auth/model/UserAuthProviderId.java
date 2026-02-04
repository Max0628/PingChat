package com.pingchat.pingchat_backend.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
public class UserAuthProviderId implements Serializable {
  private UUID userId;
  private String provider;
}