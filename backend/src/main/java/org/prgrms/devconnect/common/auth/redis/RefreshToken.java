package org.prgrms.devconnect.common.auth.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Index;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {

  @Id
  private String refreshToken;

  @Indexed
  private String authKey;

  @TimeToLive
  private Long ttl;

  @Builder
  public RefreshToken(String refreshToken, String authKey, Long ttl) {
    this.refreshToken = refreshToken;
    this.authKey = authKey;
    this.ttl = 1000L * 60 * 60 * 24 * 14;
  }
}
