package org.prgrms.devconnect.common.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    log.info("로그인 실패");
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/plain;charset=UTF-8");
    response.getWriter().write("login failed");
  }
}
