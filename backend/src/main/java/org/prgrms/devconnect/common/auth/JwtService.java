package org.prgrms.devconnect.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.devconnect.common.auth.redis.RefreshToken;
import org.prgrms.devconnect.common.auth.redis.RefreshTokenRepository;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.common.exception.refresh.RefreshTokenException;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
  private final MemberRepository memberRepository;


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
    setRefreshTokenCookie(response, refreshToken);
  }

  public Optional<String> extractAccessToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(accessHeader))
        .filter(accessToken -> accessToken.startsWith(BEARER))
        .map(accessToken -> accessToken.replace(BEARER, ""));
  }

  public Optional<String> extractUsername(String accessToken) {
    return Optional.ofNullable(getClaimsJws(accessToken).getPayload()
        .get("username", String.class));
  }

  private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader, BEARER + accessToken);
  }

  private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    Cookie cookie = new Cookie(refreshHeader, refreshToken);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge((int) (refreshTokenExpirationPeriod / 1000));
    response.addCookie(cookie);
  }

  public boolean isTokenValid(String token) {
    getClaimsJws(token);
    return true;
  }

  private Jws<Claims> getClaimsJws(String token) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token);
  }

  public void reIssueAccessToken(HttpServletResponse response, String refreshToken) {
    log.info("Access Token 재발급 시도: {}", refreshToken);
    refreshTokenRepository.findByRefreshToken(refreshToken)
        .ifPresentOrElse(token -> {
          String username = token.getAuthKey();
          Member member = memberRepository.findByEmail(username)
              .orElseThrow(() -> new MemberException(ExceptionCode.NOT_FOUND_MEMBER));
          String reIssueAccessToken = this.createAccessToken(username);
          this.setAccessTokenHeader(response, reIssueAccessToken);

          log.info("Access Token 재발급 성공");
        }, () -> {
          log.warn("Access Token 발급 실패");
          throw new RefreshTokenException(ExceptionCode.NOT_FOUND_REFRESH_TOKEN);
        });
  }
}
