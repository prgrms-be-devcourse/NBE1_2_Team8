package org.prgrms.devconnect.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // 클라이언트가 구독할 수 있는 메시지 브로커 설정
    config.enableSimpleBroker("/topic");
    // 클라이언트가 서버에 메시지를 보낼 때 사용하는 경로 prefix 설정
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // 클라이언트가 연결할 WebSocket 엔드포인트
    registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
//            .setAllowedOrigins("*")
            .withSockJS(); // SockJS 사용 설정 (WebSocket을 지원하지 않는 브라우저를 위해)
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setMessageSizeLimit(8192);
  }
}
