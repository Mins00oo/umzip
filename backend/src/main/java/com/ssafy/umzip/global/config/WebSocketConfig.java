package com.ssafy.umzip.global.config;

import com.ssafy.umzip.global.util.chat.AuthHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Slf4j
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*")
                .addInterceptors(new AuthHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");

        log.info("RabbitMQ Password: {}", password);  // 로그에 출력해 확인

        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost(host)      // RabbitMQ 서버 주소
                .setRelayPort(port)            // STOMP 포트 (기본: 61613)
                .setClientLogin(username)        // RabbitMQ 사용자명
                .setClientPasscode(password);    // RabbitMQ 비밀번호
    }

}
