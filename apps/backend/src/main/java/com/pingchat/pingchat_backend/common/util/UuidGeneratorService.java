package com.pingchat.pingchat_backend.common.util;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class UuidGeneratorService {
  /**
   * // Generates a time-ordered UUID version 7
   * @return
   */
  public UUID generateUuidV7() {

    return UuidCreator.getTimeOrdered();
  }
}
