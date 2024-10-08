package org.prgrms.devconnect.domain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.PathItem;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.auth.CustomerMemberDetailsService;
import org.prgrms.devconnect.common.auth.JwtService;
import org.prgrms.devconnect.common.auth.filter.JwtTokenAuthenticationFilter;
import org.prgrms.devconnect.common.auth.filter.LoginAuthenticationFilter;
import org.prgrms.devconnect.common.auth.handler.LoginFailureHandler;
import org.prgrms.devconnect.common.auth.handler.LoginSuccessHandler;
import org.prgrms.devconnect.common.auth.redis.RefreshTokenRepository;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtService jwtService;
  private final MemberRepository memberRepository;
  private final CustomerMemberDetailsService customerMemberDetailsService;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors((AbstractHttpConfigurer::disable))
        .csrf(CsrfConfigurer<HttpSecurity>::disable)
        .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
        .httpBasic(HttpBasicConfigurer<HttpSecurity>::disable)
        .headers(it -> it.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .sessionManagement(it ->
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/static/**", "/templates/**").permitAll()
                // 멤버
                .requestMatchers("/api/v1/members/login", "/api/v1/members/signup",
                    "/api/v1/members/reissue").permitAll()
                .requestMatchers("/api/v1/members/logout", "/api/v1/members").authenticated()
                // 알림
                .requestMatchers("/api/v1/alarms/**").authenticated()
                // 게시판
                .requestMatchers(HttpMethod.GET, "/api/v1/boards/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/boards/interests").authenticated()
                .requestMatchers("/api/v1/boards/**").authenticated()
                // 버그 리포트
                .requestMatchers("/api/v1/bug-report/**").authenticated()
                // 댓글
                .requestMatchers(HttpMethod.GET, "/api/v1/comments/**").permitAll()
                .requestMatchers("/api/v1/comments/**").authenticated()
                // 관심
                .requestMatchers("/api/v1/interests/**").authenticated()
                // 채용 공고
                .requestMatchers(HttpMethod.DELETE,"/api/v1/job-posts/**").authenticated()
                .requestMatchers( "/api/v1/job-posts/**").permitAll()
                .anyRequest().permitAll()
        );

    http.addFilterAfter(loginAuthenticationFilter(), LogoutFilter.class);
    http.addFilterBefore(jwtAuthenticationFilter(), LoginAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(customerMemberDetailsService);
    return new ProviderManager(provider);
  }

  @Bean
  JwtTokenAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtTokenAuthenticationFilter(jwtService, memberRepository);
  }

  @Bean
  LoginAuthenticationFilter loginAuthenticationFilter() {
    LoginAuthenticationFilter customUsernamePasswordAuthenticationFilter
        = new LoginAuthenticationFilter(objectMapper);
    customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
    customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(
        loginSuccessHandler());
    customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(
        loginFailureHandler());
    return customUsernamePasswordAuthenticationFilter;
  }

  @Bean
  public LoginSuccessHandler loginSuccessHandler() {
    return new LoginSuccessHandler(jwtService);
  }

  @Bean
  public LoginFailureHandler loginFailureHandler() {
    return new LoginFailureHandler();
  }

}
