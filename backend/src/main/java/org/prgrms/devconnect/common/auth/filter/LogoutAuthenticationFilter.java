package org.prgrms.devconnect.common.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.auth.JwtService;
import org.prgrms.devconnect.common.auth.redis.RefreshTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class LogoutAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final RefreshTokenRepository refreshTokenRepository;

  private static final String LOGOUT_URL = "/api/v1/members/logout";


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    if (isLogoutRequest(request)) {
      handleLogout(request, response);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private boolean isLogoutRequest(HttpServletRequest request) {
    return LOGOUT_URL.equals(request.getRequestURI()) && "POST".equalsIgnoreCase(
        request.getMethod());
  }

  private void handleLogout(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String refreshToken = jwtService.extractRefreshToken(request)
        .filter(jwtService::isTokenValid)
        .orElse(null);

    if (refreshToken != null) {
      refreshTokenRepository.deleteByRefreshToken(refreshToken);
      sendResponse(response, HttpServletResponse.SC_OK, "Successfully logged out");
    } else {
      sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid Refresh Token");
    }
  }

  private void sendResponse(HttpServletResponse response, int statusCode, String message)
      throws IOException {
    response.setStatus(statusCode);
    response.setContentType("text/plain;charset=UTF-8");
    response.getWriter().write(message);
  }
}
