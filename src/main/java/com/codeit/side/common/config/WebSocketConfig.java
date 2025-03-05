package com.codeit.side.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtWebSocketInterceptor jwtWebSocketInterceptor;

    @Override //STOMP 엔드포인트 설정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(jwtWebSocketInterceptor) // /ws 경로에 한하여 인터셉터 추가
                .withSockJS()
                .setSuppressCors(true);
    }

    /**
     * 개인메시지는 '/queue/private/{userId} 로 라우팅
     * 수신자는 '/user/queue/private/{userId}'를 구독
     * 발신자는 '/app/chat/private' 으로 메시지를 보냄
     * */
    @Override //메시지 라우팅
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }

}
