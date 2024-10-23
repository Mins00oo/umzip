package com.ssafy.umzip.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@Slf4j
public class RabbitMQConfig {
    private static final String CHAT_QUEUE_NAME = "chat.queue";
    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";
    private static final String ROUTING_KEY = "room.*";  // 여러 채팅방 처리

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    // Queue 등록
    @Bean
    public Queue queue() {
        log.info("Creating RabbitMQ Queue: {}", CHAT_QUEUE_NAME);

        return new Queue(CHAT_QUEUE_NAME, true);  // durable 큐 생성
    }

    // Exchange 등록
    @Bean
    public TopicExchange exchange() {
        log.info("Creating RabbitMQ Exchange: {}", CHAT_EXCHANGE_NAME);
        return new TopicExchange(CHAT_EXCHANGE_NAME, true, false);  // durable, auto-delete false
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        log.info("Initializing RabbitAdmin for RabbitMQ management.");

        return new RabbitAdmin(connectionFactory);
    }

    // Exchange와 Queue 바인딩
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);  // 라우팅 키를 통해 메시지 전달
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        log.info("Configuring ConnectionFactory to connect to RabbitMQ: {}:{}", host, port);

        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);  // 호스트 설정
        factory.setVirtualHost("/");  // 기본 VHost
        factory.setUsername(username);  // 사용자명
        factory.setPassword(password);  // 비밀번호
        factory.setPort(port);  // 포트
        return factory;
    }

    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        log.info("Creating SimpleRabbitListenerContainerFactory.");

        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}