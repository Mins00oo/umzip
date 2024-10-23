package com.ssafy.umzip.global.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("chatroom-exchange");
    }

    @Bean
    public Queue chatroomQueue() {
        return new Queue("chatroom-queue");
    }

    @Bean
    public Binding binding(TopicExchange topicExchange, Queue chatroomQueue) {
        return BindingBuilder.bind(chatroomQueue).to(topicExchange).with("chatroom.#");
    }
}
