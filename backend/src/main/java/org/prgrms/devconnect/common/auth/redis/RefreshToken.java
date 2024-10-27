package org.prgrms.devconnect.common.auth.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = RefreshToken.TTL)
public class RefreshToken {

  public static final long TTL = 60 * 60 * 24 * 14; // 14 days in seconds

  @Id
  private String refreshToken;

  @Indexed
  private String userEmail;

  @TimeToLive
  private Long ttl;

  @Builder
  public RefreshToken(String refreshToken, String userEmail) {
    this.refreshToken = refreshToken;
    this.userEmail = userEmail;
    this.ttl = TTL;
  }
}
