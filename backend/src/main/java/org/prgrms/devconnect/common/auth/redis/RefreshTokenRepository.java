package org.prgrms.devconnect.common.auth.redis;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByRefreshToken(String refreshToken);

  Optional<RefreshToken> findByAuthKey(String authKey);
}
