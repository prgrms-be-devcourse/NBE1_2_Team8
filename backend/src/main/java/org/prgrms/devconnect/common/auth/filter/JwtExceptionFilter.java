package org.prgrms.devconnect.common.auth.filter;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.devconnect.common.exception.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      log.warn("JWT 예외 발생: {}", e.getMessage());
      sendResponse(response, e);
    }
  }

  private void sendResponse(HttpServletResponse response, JwtException e) throws IOException {
    response.setStatus(SC_UNAUTHORIZED);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/plain;charset=UTF-8");
    response.getWriter().write(e.getMessage());
  }
}
