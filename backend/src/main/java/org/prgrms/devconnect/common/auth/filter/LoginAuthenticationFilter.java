package org.prgrms.devconnect.common.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberLoginRequestDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String REQUEST_URL = "/api/v1/members/login";
  private static final String HTTP_METHOD = "POST";

  private static final AntPathRequestMatcher PATH_REQUEST_MATCHER
      = new AntPathRequestMatcher(REQUEST_URL, HTTP_METHOD);

  private final ObjectMapper objectMapper;


  public LoginAuthenticationFilter(ObjectMapper objectMapper) {
    super(PATH_REQUEST_MATCHER);
    this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

    MemberLoginRequestDto dto = objectMapper.readValue(request.getInputStream(),
        MemberLoginRequestDto.class);

    String email = dto.email();
    String password = dto.password();

    log.info("인증 시도 유저: {}", email);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        email, password);

    return this.getAuthenticationManager().authenticate(authenticationToken);
  }
}
