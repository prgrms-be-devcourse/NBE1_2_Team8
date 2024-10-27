package org.prgrms.devconnect.common.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.devconnect.common.auth.JwtService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final MemberRepository memberRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {

    if (isRequestAuthorized(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    authenticateAccessToken(request, response, filterChain);
  }

  private boolean isRequestAuthorized(HttpServletRequest request) {
    Optional<String> accessToken = jwtService.extractAccessToken(request);
    return accessToken.isEmpty();
  }

  private void authenticateAccessToken(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    log.info("인증 시도");
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
    log.info("인증 완료: {}", member.getEmail());
  }

}
