package org.prgrms.devconnect.common.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.auth.JwtService;
import org.prgrms.devconnect.common.auth.redis.RefreshTokenRepository;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  private static final String NO_CHECK_URL = "/api/v1/members/login";
  private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

  private final JwtService jwtService;
  private final RefreshTokenRepository refreshTokenRepository;
  private final MemberRepository memberRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (isLoginRequest(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String refreshToken = extractRefreshToken(request);
    if (refreshToken != null) {
      reIssueAccessToken(response, refreshToken);
      return;
    }

    authenticateAccessToken(request, response, filterChain);
  }

  private boolean isLoginRequest(HttpServletRequest request) {
    return request.getRequestURI().equals(NO_CHECK_URL);
  }

  private String extractRefreshToken(HttpServletRequest request) {
    return jwtService.extractRefreshToken(request)
        .filter(jwtService::isTokenValid)
        .orElse(null);
  }

  private void reIssueAccessToken(HttpServletResponse response, String refreshToken) {
    refreshTokenRepository.findByRefreshToken(refreshToken)
        .ifPresentOrElse(token -> {
          String username = token.getAuthKey();
          Member member = memberRepository.findByEmail(username)
              .orElseThrow(() -> new MemberException(ExceptionCode.NOT_FOUND_MEMBER));
          String reIssuedRefreshToken = jwtService.createRefreshToken(username);
          jwtService.sendAccessAndRefreshToken(response,
              jwtService.createAccessToken(username),
              reIssuedRefreshToken);
        }, () -> logger.warn("Refresh token not found: {}", refreshToken));
  }

  private void authenticateAccessToken(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    jwtService.extractAccessToken(request)
        .filter(jwtService::isTokenValid)
        .flatMap(jwtService::extractUsername)
        .flatMap(memberRepository::findByEmail)
        .ifPresent(this::saveAuthentication);

    filterChain.doFilter(request, response);
  }

  private void saveAuthentication(Member member) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(member, null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    logger.info("인증 유저: {}", member.getEmail());
  }
}
