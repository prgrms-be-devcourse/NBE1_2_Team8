package org.prgrms.devconnect.common.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.devconnect.common.auth.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String username = extractUsername(authentication);
    String accessToken = jwtService.createAccessToken(username);
    String refreshToken = jwtService.createRefreshToken(username);
    jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
    log.info("로그인 성공: {}", username);
    log.info("Access & refresh 토큰 생성");
  }

  private String extractUsername(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userDetails.getUsername();
  }
}
