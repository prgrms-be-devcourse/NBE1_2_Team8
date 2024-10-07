package org.prgrms.devconnect.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.auth.redis.RefreshToken;
import org.prgrms.devconnect.common.auth.redis.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.access.expiration}")
  private Long accessTokenExpirationPeriod;

  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenExpirationPeriod;

  @Value("${jwt.access.header}")
  private String accessHeader;

  @Value("${jwt.refresh.header}")
  private String refreshHeader;

  private static final String BEARER = "Bearer ";
  private final RefreshTokenRepository refreshTokenRepository;


  private SecretKey getSecretKey() {
    return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
  }

  public String createAccessToken(String username) {
    return Jwts.builder()
        .claim("username", username)
        .claim("category", "AccessToken")
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationPeriod))
        .signWith(getSecretKey())
        .compact();
  }

  public String createRefreshToken(String username) {
    String token = Jwts.builder()
        .claim("username", username)
        .claim("category", "RefreshToken")
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
        .signWith(getSecretKey())
        .compact();

    RefreshToken refreshToken = RefreshToken.builder()
        .refreshToken(token)
        .authKey(username)
        .build();

    refreshTokenRepository.save(refreshToken);
    return token;
  }

  public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken,
      String refreshToken) {
    response.setStatus(HttpServletResponse.SC_OK);
    setAccessTokenHeader(response, accessToken);
    setRefreshTokenHeader(response, refreshToken);
  }

  public Optional<String> extractRefreshToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(refreshHeader))
        .filter(refreshToken -> refreshToken.startsWith(BEARER))
        .map(refreshToken -> refreshToken.replace(BEARER, ""));
  }

  public Optional<String> extractAccessToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(accessHeader))
        .filter(accessToken -> accessToken.startsWith(BEARER))
        .map(accessToken -> accessToken.replace(BEARER, ""));
  }

  public Optional<String> extractUsername(String accessToken) {
    try {
      return Optional.ofNullable(getClaimsJws(accessToken).getPayload()
          .get("username", String.class));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader, BEARER + accessToken);
  }

  private void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
    response.setHeader(refreshHeader, BEARER + refreshToken);
  }

  public boolean isTokenValid(String token) {
    try {
      getClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Jws<Claims> getClaimsJws(String token) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token);
  }

}
