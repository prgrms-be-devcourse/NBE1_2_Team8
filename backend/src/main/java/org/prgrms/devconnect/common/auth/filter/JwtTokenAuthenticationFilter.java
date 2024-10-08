package org.prgrms.devconnect.common.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.devconnect.common.auth.JwtService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  private static final String NO_CHECK_URL = "/api/v1/members/login";

  private final JwtService jwtService;
  private final MemberRepository memberRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException {
    try {

      if (isRequestAuthorized(request)) {
        filterChain.doFilter(request, response);
        return;
      }

      authenticateAccessToken(request, response, filterChain);

    } catch (ExpiredJwtException e) {
      jwtExceptionHandler(response, ExceptionCode.JWT_TOKEN_EXPIRED);
    } catch (UnsupportedJwtException e) {
      jwtExceptionHandler(response, ExceptionCode.JWT_TOKEN_UNSUPPORTED);
    } catch (MalformedJwtException e) {
      jwtExceptionHandler(response, ExceptionCode.JWT_TOKEN_MALFORMED);
    } catch (SignatureException e) {
      jwtExceptionHandler(response, ExceptionCode.JWT_SIGNATURE_INVALID);
    } catch (IllegalArgumentException e) {
      jwtExceptionHandler(response, ExceptionCode.JWT_ARGUMENT_INVALID);
    } catch (JwtException e) {
      jwtExceptionHandler(response, ExceptionCode.JWT_GENERAL_ERROR);
    } catch (Exception e) {
      log.error("예기치 않은 오류 발생: {}", e.getMessage());
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.setCharacterEncoding("UTF-8");
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write("{\"error\":\"예기치 않은 오류가 발생했습니다.\"}");
    }
  }

  private boolean isRequestAuthorized(HttpServletRequest request) {
    Optional<String> accessToken = jwtService.extractAccessToken(request);

    return accessToken.isEmpty() || isLoginRequest(request);
  }

  private void jwtExceptionHandler(HttpServletResponse response, ExceptionCode exceptionCode)
      throws IOException {
    log.error(">>>> [ JWT 토큰 인증 중 Error 발생 : {} ] <<<<", exceptionCode.getMessage());

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setCharacterEncoding("utf-8");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write("{\"error\":\"" + exceptionCode.getMessage() + "\"}");
  }

  private boolean isLoginRequest(HttpServletRequest request) {
    return request.getRequestURI().equals(NO_CHECK_URL) && "POST".equalsIgnoreCase(
        request.getMethod());
  }

  private void authenticateAccessToken(HttpServletRequest request,
      HttpServletResponse response,
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
    log.info("인증 완료 유저: {}", member.getEmail());
  }
}
